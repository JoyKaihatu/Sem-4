import random
class AI:
    def __init__(self):
        self.boardLangkah = [[None for _ in range(8)] for _ in range(8)]
        self.boardData = [[None for _ in range(8)] for _ in range(8)]
        self.lastMove = [0, 0]
        self.sinkShip = [False, False, False]
        self.boardFocus = False
        self.aiFocusFire = [[]]
        self.focusFireFlag = False
        self.firstContact = [0, 0]
        self.count = 0

    def picky(self, onHitStatus: bool, sinkStatus: bool):

        if onHitStatus == False and self.focusFireFlag == False:
            x, y = self.normalMove()
            return x, y
        elif sinkStatus == True:
            self.aiFocusFire = [[]]
            self.focusFireFlag = False
            x, y = self.normalMove()
            return x, y
        elif onHitStatus == True and self.focusFireFlag == False:
            self.focusFireFlag = True
            x, y = self.focusFire()
            return x, y

        elif onHitStatus == True and self.focusFireFlag == True:
            x, y = self.focusFire()
            return x, y

        elif onHitStatus == False and self.focusFireFlag == True and sinkStatus == False:
            x,y = self.focusFire()
            return x,y

    # If isHit == True, Fungsi ini yang akan dijlankan
    def focusFire(self):
        pilih = [self.lastMove[0],self.lastMove[1]]


        ## Cek Vertical Ke atas is possible or not
        if pilih[0] + 1 < len(self.boardLangkah) and self.count == 0:
            if self.boardLangkah[pilih[0] + 1][pilih[1]] is None:
                return pilih[0] + 1, pilih[1]

        ## Cek Vertical Ke bawah is possible or Not
        if pilih[0] - 1 >= 0 and self.count == 1:
            if self.boardLangkah[pilih[0] - 1][pilih[1]] is None:
                return pilih[0] + 1, pilih[1]

        ## Cek Horizontal ke kanan is possible or Not
        if pilih[1] + 1 < len(self.boardLangkah) and self.count == 2:
            if self.boardLangkah[pilih[0]][pilih[1] + 1] is None:
                return self.lastMove[0], self.lastMove[1] + 1

        ## Cek Horizontal ke kiri is possible or Not
        if pilih[1] - 1 >= 0 and self.count == 3:
            if self.boardLangkah[pilih[0]][pilih[1] + 1] is None:
                return pilih[0], pilih[1] + 1




    def normalMove(self):
        while (True):
            row = random.randint(0, 7)
            column = random.randint(0, 7)

            if self.boardLangkah[row][column] == None:
                self.lastMove[0] = row
                self.lastMove[1] = column
                return row, column

    def populateBoard(self):
        centerx, centery = 0, 0

    def makeBoard(self):
        if self.sinkShip[0] == True:
            self.aiFocusFire = [[[0, 0] for _ in range(3)] for _ in range(3)]
        else:
            self.aiFocusFire = [[[0, 0] for _ in range(5) for _ in range(5)]]

