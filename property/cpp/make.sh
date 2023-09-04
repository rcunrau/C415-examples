# run from .
antlr4 PropertyFile.g4 -visitor -Dlanguage=Cpp
clang -c *.cpp -std=c++17 -I$ANTLR_INS/include/antlr4-runtime
clang -o TestPropertyFile main.o P*.o $ANTLR_INS/lib/libantlr4-runtime.a -lc++
