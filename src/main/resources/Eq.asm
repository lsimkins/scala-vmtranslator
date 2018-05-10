// Headers
@256
D=A
@SP
M=D

// PC: 3
// push constant 7
@7
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 10
// push constant 8
@8
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 17
// eq
@24
D=A
@15
M=D
@EQ
0;JMP

// PC: 23
// push constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 30
// push constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 37
// eq
@44
D=A
@15
M=D
@EQ
0;JMP

// Footers
@END
0;JMP

(EQ)
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@EQ2
M;JEQ
M=-1
(EQ2)
@SP
A=M
M=!M
@SP
M=M+1

@15
A=M
0;JMP

(LT)
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@LT2
M;JGT
@SP
A=M
M=0
@SP
M=M+1

@15
A=M
0;JMP

(LT2)
@SP
A=M
M=-1
@SP
M=M+1

@15
A=M
0;JMP

(END)
D=0
// Headers
@256
D=A
@SP
M=D

// PC: 3
// push constant 7
@7
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 10
// push constant 8
@8
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 17
// lt
@24
D=A
@15
M=D
@LT
0;JMP

// PC: 23
// push constant 5
@5
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 30
// push constant 5
@5
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 37
// lt
@44
D=A
@15
M=D
@LT
0;JMP

// PC: 43
// push constant 5
@5
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 50
// push constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 57
// lt
@64
D=A
@15
M=D
@LT
0;JMP

// Footers
@END
0;JMP

(EQ)
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@EQ2
M;JEQ
M=-1
(EQ2)
@SP
A=M
M=!M
@SP
M=M+1

@15
A=M
0;JMP

(LT)
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@LT2
M;JGT
@SP
A=M
M=0
@SP
M=M+1

@15
A=M
0;JMP

(LT2)
@SP
A=M
M=-1
@SP
M=M+1

@15
A=M
0;JMP

(END)
D=0