import java.io._

val LOCAL_REPO = "D:/dropbox/My Dropbox/remeniuk.github.com/" 
val IGNORE_FILE = List(".git", "index.html", "updateIndices.bat", "updateIndices.scala")

def updateIndex(currentDir: String = ""):Unit = {
	val currentPath = LOCAL_REPO + currentDir
	println("Updating indeces in " + currentPath)
	val loc = new File(currentPath)
	if(loc.isDirectory){
		loc.listFiles.find(_.getName == "index.html").
			map(_.delete)
		new File(currentPath + "/index.html").createNewFile
		val out = new java.io.FileWriter(currentPath + "/index.html")
		out.write(
			(<html>
				<h1>Maven Repository remeniuk@github</h1>
				<ul>{loc.listFiles.filterNot(file => IGNORE_FILE.contains(file.getName))
				.map(file => <li><a href={"http://remeniuk.github.com" + currentDir +  "/" + file.getName}>{
				(if(file.isDirectory) "/" else "") + file.getName}</a></li>)}</ul> 
			</html>) toString
		)
		out.close
		loc.listFiles.filter(_.isDirectory).filterNot(file => IGNORE_FILE.contains(file.getName)).foreach(dir => updateIndex(currentDir + "/" + dir.getName))
	}
}

updateIndex()