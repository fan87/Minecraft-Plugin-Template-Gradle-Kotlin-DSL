 
import sys
import os
from os import path

DONT_CHANGE_PLACEHOLDER = "//DONT_APPLY_TEMPLATE_CHANGE\n"

def generate_maven_name(inp: str) -> str:
    maven_artifactid_name = ""

    char_index = 0
    for c in inp:
        char_index += 1
        if c.isupper() and char_index > 1 and inp[char_index - 2] != "-":
            maven_artifactid_name += "-"
        maven_artifactid_name += c.lower()
    return maven_artifactid_name

def apply(original: str, repo_name: str) -> str:
    original = original.replace("gradle-plugin-template", generate_maven_name(repo_name))
    original = original.replace("gradleplugintemplate", repo_name.replace("-", "").lower())
    original = original.replace("GradlePluginTemplate", repo_name.replace("-", ""))
    original = original.replace("Gradle-Plugin-Template", repo_name)
    return original


def run(repo_name: str, base_dir: str):
    for file in os.listdir(base_dir):
        if file.startswith(".git"):
            continue
        new_file = apply(file, repo_name)
        os.rename(base_dir + file, base_dir + new_file)
        if path.isdir(base_dir + new_file):
            run(repo_name, base_dir + new_file + "/")
        else:
            if not (new_file.endswith(".kt") or new_file.endswith(".kts") or new_file.endswith(".properties") or new_file.endswith(".java") or new_file.endswith(".yml") or new_file.endswith(".mcmeta") or new_file.endswith("LICENSE") or new_file.endswith(".gitignore")):
                continue
            reader = open(base_dir + new_file, "r", errors="ignore")
            data = reader.read()
            reader.close()
            if not data.startswith(DONT_CHANGE_PLACEHOLDER):
                data = apply(data, repo_name)
            else:
                data = data[len(DONT_CHANGE_PLACEHOLDER) : len(data)]
            writer = open(base_dir + new_file, "w", errors="ignore")
            writer.write(data)
            writer.close()

if __name__ == "__main__":
    repo_name = sys.argv[1]
    run(repo_name, "./")
