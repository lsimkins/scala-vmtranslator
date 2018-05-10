// Headers
@256
D=A
@SP
M=D

// PC: 3
// push constant 10
@10
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 10
// pop local 0
@LCL
D=M
@0
A=D+A
D=A
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D

// PC: 25
// push constant 21
@21
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 32
// push constant 22
@22
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 39
// pop argument 2
@ARG
D=M
@2
A=D+A
D=A
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D

// PC: 54
// pop argument 1
@ARG
D=M
@1
A=D+A
D=A
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D

// PC: 69
// push constant 36
@36
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 76
// pop this 6
@THIS
D=M
@6
A=D+A
D=A
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D

// PC: 91
// push constant 42
@42
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 98
// push constant 45
@45
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 105
// pop that 5
@THAT
D=M
@5
A=D+A
D=A
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D

// PC: 120
// pop that 2
@THAT
D=M
@2
A=D+A
D=A
@13
M=D
@SP
M=M-1
@SP
A=M
D=M
@13
A=M
M=D

// PC: 135
// push constant 510
@510
D=A
@SP
A=M
M=D
@SP
M=M+1

// PC: 142
// pop temp 6
@SP
M=M-1
@SP
A=M
D=M
@11
M=D

// PC: 149
// push local 0
@LCL
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// PC: 159
// push that 5
@THAT
D=M
@5
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// PC: 169
// add
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M+D
@SP
M=M+1

// PC: 181
// push argument 1
@ARG
D=M
@1
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// PC: 191
// sub
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
@SP
M=M+1

// PC: 203
// push this 6
@THIS
D=M
@6
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// PC: 213
// push this 6
@THIS
D=M
@6
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// PC: 223
// add
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M+D
@SP
M=M+1

// PC: 235
// sub
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
@SP
M=M+1

// PC: 247
// push temp 6
@11
D=M
@SP
A=M
M=D
@SP
M=M+1

// PC: 254
// add
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M+D
@SP
M=M+1

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
D=M
@EQ2
D;JEQ
@SP
A=M
M=0
@SP
M=M+1

@15
A=M
0;JMP
    
(EQ2)
@SP
A=M
M=-1
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
D=M
@LT2
D;JGE
@SP
A=M
M=-1
@SP
M=M+1

@15
A=M
0;JMP
    
(LT2)
@SP
A=M
M=0
@SP
M=M+1

@15
A=M
0;JMP

(GT)
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
D=M
@LT2
D;JLE
@SP
A=M
M=-1
@SP
M=M+1

@15
A=M
0;JMP
    
(LT2)
@SP
A=M
M=0
@SP
M=M+1

@15
A=M
0;JMP

(END)
D=0