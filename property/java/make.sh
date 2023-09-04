# run from ..
antlr4 PropertyFile.g4 -visitor -o java
# run from .
javac *.java
java TestPropertyFile
