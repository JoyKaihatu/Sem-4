import pprint

class board:
    def __init__(self):
        self.board = [[0]*10 for _ in range(10)]
        self.board2 = [[1]*10 for _ in range(10)]

        self.ship = 2
        self.ship1 = 2
        self.ship2 = 3
        self.ship3 = 4
        self.ship4 = 5

    def pickBoard(self, row,column):
        self.board[row][column] = 1
        self.checkHit(row,column)

    def checkHit(self,row,column):
        if self.board2[row][column] == 1:
            print("HIT")

    def getboard(self):
        return self.board



tes = board()
tes.pickBoard(9,9)


pprint.pprint(tes.board)