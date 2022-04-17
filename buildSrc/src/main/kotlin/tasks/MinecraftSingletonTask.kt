package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.VarInsnNode
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.Modifier
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

open class MinecraftSingletonTask: DefaultTask() {
    @InputFile
    val input: RegularFileProperty = project.objects.fileProperty()

    init {
        this.input.set(File(project.buildDir, "libs/" + project.name + "-" + project.version + ".jar"))
    }

    @TaskAction
    fun action() {

        val inputStream = ZipInputStream(FileInputStream(input.get().asFile))
         val yaml = Yaml()

        val zipContent = HashMap<String, ByteArray>()
        var mainClass = ""
        var entry = inputStream.nextEntry
        while(entry != null) {
            val content: ByteArray = inputStream.readBytes()

            if (entry.name == "plugin.yml") {
                val load = yaml.load<Map<String, Any>>(String(content))
                mainClass = (load["main"] as String).replace(".", "/") + ".class"
                project.logger.info("Main Class: $mainClass")
            }

            zipContent[entry.name] = content

            inputStream.closeEntry()
            entry = inputStream.nextEntry
        }
        inputStream.close()
        val outputStream = ZipOutputStream(FileOutputStream(File(project.buildDir, "libs/" + project.name + "-" + project.version + "-singleton.jar")))

        for (zipEntry in zipContent) {
            outputStream.putNextEntry(ZipEntry(zipEntry.key))
            if (zipEntry.key == mainClass) {
                project.logger.info("Successfully found class: " + zipEntry.key)
                val classNode = ClassNode()
                val reader = ClassReader(zipEntry.value)
                val writer = ClassWriter(0)
                reader.accept(classNode, 0)
                var nodeToInsert: AbstractInsnNode? = null
                for (method in classNode.methods) {
                    if (method.name == "<clinit>") {
                        for (i in 1..3) {
                            method.instructions.remove(method.instructions.first)
                        }
                        nodeToInsert = method.instructions.first
                        method.instructions.remove(nodeToInsert)
                        project.logger.info("\tSuccessfully changed <clinit> method of ${classNode.name}")
                    }
                }
                if (nodeToInsert == null) return;
                for (method in classNode.methods) {
                    if (method.name == "<init>") {
                        if (!Modifier.isPrivate(method.access)) return
                        method.access = method.access or Modifier.PUBLIC
                        method.access = removeModifierFlag(method.access, Modifier.PRIVATE)
                        method.instructions.insert(nodeToInsert)
                        method.instructions.insert(VarInsnNode(Opcodes.ALOAD, 0))
                        project.logger.info("\tSuccessfully changed <init> method (constructor) of ${classNode.name}")
                    }
                }
                classNode.accept(writer)
                outputStream.write(writer.toByteArray())
            } else {
                outputStream.write(zipEntry.value)
            }

            outputStream.closeEntry()
        }
        outputStream.close()

        input.get().asFile.writeBytes(
            File(project.buildDir, "libs/" + project.name + "-" + project.version + "-singleton.jar").readBytes()
        )
//        File(project.buildDir, "libs/" + project.name + "-" + project.version + "-singleton.jar").delete()
    }

    fun removeModifierFlag(modifier: Int, flag: Int): Int {
        return if (modifier and flag != 0) {
            modifier xor flag
        } else modifier
    }

}
