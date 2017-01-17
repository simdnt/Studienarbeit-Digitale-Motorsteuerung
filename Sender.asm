mov r11,#1
:start
movx r9,r10;r9=r10=Eingabewert
call :TransmissionBegin
call :sendR9
jmp :start

:sendR9
xby r1;schreibe 0
call :waitLong
mov r0,#-8; gehe durch die Acht Stellen
:send_0
inc r0
xby r9; sende letzte Stelle
shr r9; shift rechts um 1
call :waitLong
jne r0,:send_0
ret


:TransmissionBegin
push r12
mov r12,#-20; initialisiert zaehlvariable mit -20
xby r11; schreibt die 1 raus
:TB_0
inc r12
call :waitLong; wartet 20 Mal
jne r12,:TB_0
pop r12
ret

:waitLong
push r12
mov r12,#-10
:wait_0
inc r12
jne r12,:wait_0
pop r12
ret