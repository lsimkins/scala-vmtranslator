# VMTranslator for Nand2Tetris

This is the VMTranslator for projects 7 and 8 in the Nand2Tetris course. The purpose is
to translate the virtual machine stack language into hack assembly code.

You may translate a .vm file, or folder containing .vm files making up a program, by passing
the file or folder path as the first argument when the translator. The output will be a single
.asm file with the same basename as the file/folder.

```bash
sbt run [file|folder_path]
```

## Building
The project can be built & zipped by calling `sh build/build.sh`. The output will be a .zip
file called `project7.zip` containing a .jar file, a Makefile (that does nothing, but is needed
when submitting a .jar file), a bash script that runs the program, and a lang.text file.