
import org.eclipse.jgit.api.Git
import java.io.File

object GitUtils {

    fun getCommitId(dir: File): String {
        val git = Git.open(dir)


        for (commit in git.log().setMaxCount(2).call()) {
            return commit.id.name
        }
        return "null"
    }

}