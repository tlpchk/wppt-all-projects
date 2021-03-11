doublefactorial(long double):
  push ebp
  mov ebp, esp
  sub esp, 20
  mov DWORD PTR [ebp-4], 1
  fld TBYTE PTR [ebp+8]
  fnstcw WORD PTR [ebp-18]
  movzx eax, WORD PTR [ebp-18]
  mov ah, 12
  mov WORD PTR [ebp-20], ax
  fldcw WORD PTR [ebp-20]
  fistp DWORD PTR [ebp-8]
  fldcw WORD PTR [ebp-18]
.L6:
  cmp DWORD PTR [ebp-8], 0
  js .L2
  cmp DWORD PTR [ebp-8], 0
  je .L3
  cmp DWORD PTR [ebp-8], 1
  jne .L4
.L3:
  fild DWORD PTR [ebp-4]
  jmp .L7
.L4:
  mov eax, DWORD PTR [ebp-4]
  imul eax, DWORD PTR [ebp-8]
  mov DWORD PTR [ebp-4], eax
  sub DWORD PTR [ebp-8], 2
  jmp .L6
.L2:
  fld DWORD PTR .LC0
  fstp st(0)
  fld DWORD PTR .LC0
.L7:
  leave
  ret
.LC1:
  .string "%Lf"
.LC2:
  .string "Double factorial is %Lf"
main:
  lea ecx, [esp+4]
  and esp, -16
  push DWORD PTR [ecx-4]
  push ebp
  mov ebp, esp
  push ecx
  sub esp, 20
  sub esp, 8
  lea eax, [ebp-24]
  push eax
  push OFFSET FLAT:.LC1
  call scanf
  add esp, 16
  fld TBYTE PTR [ebp-24]
  sub esp, 4
  lea esp, [esp-12]
  fstp TBYTE PTR [esp]
  call doublefactorial(long double)
  add esp, 16
  lea esp, [esp-12]
  fstp TBYTE PTR [esp]
  push OFFSET FLAT:.LC2
  call printf
  add esp, 16
  mov eax, 0
  mov ecx, DWORD PTR [ebp-4]
  leave
  lea esp, [ecx-4]
  ret
.LC0:
  .long 2143289344