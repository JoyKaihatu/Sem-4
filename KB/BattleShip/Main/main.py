import random
import pprint
import numpy as np
class board:
    def __init__(self):
        self.board = [[None for _ in range(8)] for _ in range(8)]
        self.list_kapal={"carrier":5, "submarine":1, "destroyer":3}
        self.NoMissFlag = False
        self.lastHit = [0,0]
    def attack(self,row,column):
        y = self.is_overlap(row,column)

        if y:
            return True, False
        elif not y:
            if self.NoMissFlag == False:
                x = self.is_hit(row,column)
                return x,x

    def is_overlap(self,row,column):
        if self.board[row][column] == "x":
            print("Kotak ini sudah di tembak")
            return True
        else:
            return False

    def is_sink(self):
        if self.list_kapal.get("carrier") == 0:
            self.list_kapal.__setitem__("carrier",self.list_kapal.get("carrier") - 1)
            print("Carrier is sink")
            return 0
        elif self.list_kapal.get("destroyer") == 0:
            self.list_kapal.__setitem__("destroyer", self.list_kapal.get("destroyer") - 1)
        elif self.list_kapal.get("submarine") == 0:
            self.list_kapal.__setitem__("submarine", self.list_kapal.get("submarine") - 1)
            print("Submarine is sink")
            return 2

    def is_hit(self,row,column):
        if self.board[row][column] in self.list_kapal:
            print("On Hit")
            self.NoMissFlag = True
            self.board[row][column] = "x"
            return True
        else:
            print("No Hit")
            self.NoMissFlag = False
            self.board[row][column] = "x"
            return False

    def isi_board(self):
        ship_list = ["carrier", "submarine", "destroyer"]
        ship_size = [5,1,3]
        ship_listPicker = 0
        while True:
            if ship_listPicker >= len(ship_list):
                break
            count = 0
            isiKapalFlag = False
            row = random.randint(0,7)
            column = random.randint(0,7)
            axis = random.randint(0,1)
            print(row,column,axis)

            if axis == 0:
                if row + ship_size[ship_listPicker] - 1 < len(self.board):
                    for i in range(row,row + ship_size[ship_listPicker]):
                        count = count + 1
                        if self.board[i][column] in self.list_kapal:
                            break
                    print("count: ", count)

                    if count == ship_size[ship_listPicker] :
                        isiKapalFlag = True
                    print("Flag Status : ", isiKapalFlag)
                    if isiKapalFlag == True:
                        print(row, ship_size[ship_listPicker])
                        for i in range(row,row + ship_size[ship_listPicker] ):
                            self.board[i][column] = str(ship_list[ship_listPicker])
                        ship_listPicker += 1

            if axis == 1:
                if column + ship_size[ship_listPicker] - 1 < len(self.board):
                    for i in range(column,column + ship_size[ship_listPicker] ):
                        count = count + 1
                        if self.board[row][i] in self.list_kapal:
                            break
                    print("count: ", count)

                    if count == ship_size[ship_listPicker]:
                        isiKapalFlag = True

                    print("Flag Status : ", isiKapalFlag)

                    if isiKapalFlag == True:

                        print(column, ship_size[ship_listPicker])
                        for i in range(column,column + ship_size[ship_listPicker] ):
                            self.board[row][i] = str(ship_list[ship_listPicker])
                        ship_listPicker += 1



class AI:
    def __init__(self):
        self.boardLangkah = [[None for _ in range(8)] for _ in range(8)]
        self.boardData = [[None for _ in range(8)] for _ in range(8)]
        self.lastMove = [0,0]
        self.sinkShip=[False,False,False]
        self.boardFocus = False
        self.aiFocusFire =[[]]
        self.firstContact = [0,0]
    def normalMove(self):
        while(True):
            row = random.randint(0,7)
            column = random.randint(0,7)

            if self.boardLangkah[row][column] == None:
                self.lastMove[0] = row
                self.lastMove[1] = column
                return row,column

    #If isHit == True, Fungsi ini yang akan dijlankan
    def focusFire(self):


        pass


    def makeBoard(self):
        if self.sinkShip[0] == True:
            self.aiFocusFire = [[[0,0] for _ in range(3)] for _ in range(3)]
        else:
            self.aiFocusFire = [[[0,0] for _ in range(5) for _ in range(5)]]




tes1 = board()
tes1.isi_board()
pprint.pprint(tes1.board)