
                opt f-g-h+l+o+
                org $1000

start           lda <text
                sta $80
                lda >text
                sta $81
				ldy #$01
                lda #%00010001 ; WPISZ LICZBĘ
                jsr bcd

                lda <text
                ldx >text
                jsr $ff80
                brk

bcd        		pha
				jsr pxdig
				pla
				lsr @
				lsr @
				lsr @
				lsr @
pxdig			and #%00011111
				ora #'0'
				cmp #'9'+1
				sta ($80),y
				dey
				rts

                org $2000

text            dta b(0)
				dta b(0)
                dta b(10) ; '\n'
                dta b(0)

                org $2E0
                dta a(start)

                end of file
