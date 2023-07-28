# Hack machine language assembler
## Contract
* Develop a HackAssembler program that translates Hack assembly programs into executable binary code.
* The source program is supplied in a text file named `Xxx.asm`
* The generated code is written into a text file named `Xxx.hack`
* Assumption: `Xxx.asm` is error-free

## Usage
```shell
java -jar HackAssembler.jar Xxx.asm
```
This command should create (or override) an `Xxx.hack` file that can be executed as is on the Hack computer.

## Design
### Modules
* Parser: unpacks each instruction into its underlying fields
* Code: translates each field into its corresponding binary value
* SymbolTable: manages the symbol table
* Main: initializes the I/O files and drives the process

## Hack assembly language specification
### Instruction types
* A-instruction: `@value` where `value` is either a non-negative decimal number or a symbol referring to such a number
* C-instruction `dest=comp;jump` where `dest` is the destination mnemonic, `comp` is the computation mnemonic, and `jump` is the jump mnemonic

#### A-instruction
Symbolic syntax: `opcode value` where opcode is `0` and value is a `15 bit address`

#### C-instruction
Symbolic syntax: `dest=comp;jump`  
where `dest` is the destination mnemonic, `comp` is the computation mnemonic, and `jump` is the jump mnemonic  

Binary syntax: `1 1 1 a c1 c2 c3 c4 c5 c6 d1 d2 d3 j1 j2 j3`  

Destinations table

| dest | d1 | d2 | d3 |
|------|----|----|----|
| null | 0  | 0  | 0  |
| M    | 0  | 0  | 1  |
| D    | 0  | 1  | 0  |
| MD   | 0  | 1  | 1  |
| A    | 1  | 0  | 0  |
| AM   | 1  | 0  | 1  |
| AD   | 1  | 1  | 0  |
| AMD  | 1  | 1  | 1  |


Jump table

| jump | j1 | j2 | j3 |
|------|----|----|----|
| null | 0  | 0  | 0  |
| JGT  | 0  | 0  | 1  |
| JEQ  | 0  | 1  | 0  |
| JGE  | 0  | 1  | 1  |
| JLT  | 1  | 0  | 0  |
| JNE  | 1  | 0  | 1  |
| JLE  | 1  | 1  | 0  |
| JMP  | 1  | 1  | 1  |


Computations table  

| compA | compD | c1 | c2 | c3 | c4 | c5 | c6 |
|-------|-------|----|----|----|----|----|----|
| 0     |       | 1  | 0  | 1  | 0  | 1  | 0  |
| 1     |       | 1  | 1  | 1  | 1  | 1  | 1  |
| -1    |       | 1  | 1  | 1  | 0  | 1  | 0  |
| D     |       | 0  | 0  | 1  | 1  | 0  | 0  |
| A     | M     | 1  | 1  | 0  | 0  | 0  | 0  |
| !D    |       | 0  | 0  | 1  | 1  | 0  | 1  |
| !A    | !M    | 1  | 1  | 0  | 0  | 0  | 1  |
| -D    |       | 0  | 0  | 1  | 1  | 1  | 1  |
| -A    | -M    | 1  | 1  | 0  | 0  | 1  | 1  |
| D+1   |       | 0  | 1  | 1  | 1  | 1  | 1  |
| A+1   | M+1   | 1  | 1  | 0  | 1  | 1  | 1  |
| D-1   |       | 0  | 0  | 1  | 1  | 1  | 0  |
| A-1   | M-1   | 1  | 1  | 0  | 0  | 1  | 0  |
| D+A   | D+M   | 0  | 0  | 0  | 0  | 1  | 0  |
| D-A   | D-M   | 0  | 1  | 0  | 0  | 1  | 1  |
| A-D   | M-D   | 0  | 0  | 0  | 1  | 1  | 1  |
| D&A   | D&M   | 0  | 0  | 0  | 0  | 0  | 0  |
| D\|A  | D\|M  | 0  | 1  | 0  | 1  | 0  | 1  |
| a=0   | a=1   |    |    |    |    |    |    |

### Symbols
* Variable symbols
  * represent memory locations where the programmer wants to maintain values
* Label symbols
  * represent destinations of goto instructions
* pre-defined symbols
  * represent special memory locations

#### There are 23 pre-defined symbols in Hack assembly language
| Symbol | Value  |
|--------|--------|
| SP     | 0      |
| LCL    | 1      |
| ARG    | 2      |
| THIS   | 3      |
| THAT   | 4      |
| R0     | 0      |
| R1     | 1      |
| R2     | 2      |
| ...    | ...    |
| R15    | 15     |
| SCREEN | 16384  |
| KBD    | 24576  |
