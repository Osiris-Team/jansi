# Note that you may need to update the version number below to run this script.
# This script is used to see if the "JVM crash when using jansi without a terminal"
# was fixed and no crashes occur anymore with the latest patch.
javaw -jar .\target\jansi-2.4.2.jar
java -jar .\target\jansi-2.4.2.jar
pause