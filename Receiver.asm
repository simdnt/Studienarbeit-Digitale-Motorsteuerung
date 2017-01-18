;For this setup a waitLong is about 6.5*6 commands
:start
call :WaitForTransaction
call :readValueToR3
movx r10,r3
jmp :start

:readValueToR3
call :waitLong
mov r3,#0
mov r0,#-8; gehe durch die Acht Stellen
mov r1,#1; Wegen rueckwaerts und so
:read_0
inc r0
mov r2,addat;
jeq r2,:next
add r3,r1;
:next
shl r1
call :waitLong
jne r0,:read_0
ret



:WaitForTransaction
mov r0,#-60;counts 0V
mov r1,#-70;counts 5V
:WFTLoop
mov r2,addat
jne r2,:5V;      if(addat=0V)
;0V                 0VCount++;
inc r0;             5VCount=0;
mov r1,#-70
jmp :continue
:5V;              else
inc r1;             5VCount++;
mov r0,#-60;        0VCount=0;
:continue

jeq r0,:disconnected
jeq r1,:waitForZero
jmp :WFTLoop

:disconnected
mov r10,#0
jmp :WaitForTransaction
:waitForZero
mov r2,addat
jne r2,:waitForZero
ret



:waitLong
push r12
mov r12,#-10
:wait_0
inc r12
jne r12,:wait_0
pop r12
ret