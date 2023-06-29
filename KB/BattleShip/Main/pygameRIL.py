import pprint
import pygame
import numpy as np
import random
import sys

pygame.init()

screen = pygame.display.set_mode((800, 600))
pygame.display.set_caption("Battleship")
black = (0, 0, 0)
white = (255, 255, 255)
tombolStart = pygame.image.load("ASET/start.png")
tombolStartRec = tombolStart.get_rect()
tombolStartRec.x = 450
tombolStartRec.y = 500

TorpedoText = 'Tidak Aktif'
EnergyCountText = '0'
font = pygame.font.Font('freesansbold.ttf', 32)
font2 = pygame.font.Font('freesansbold.ttf', 12)
text2 = font.render('Anda Menang', True, black, white)
text2Rec = text2.get_rect()
text2Rec.center = (400, 450)
text = font.render('Anda Kalah', True, black, white)
textRec = text.get_rect()
textRec.center = (400, 450)


class RenderText:

    def __init__(self):

        self.text1 = font2.render('Skill Status', True, black, white)
        self.text2 = font2.render('Torpedo: ', True, black, white)
        self.text3 = font2.render('Energy: ', True, black, white)
        self.text4 = font2.render(EnergyCountText, True, black, white)
        self.text5 = font2.render(TorpedoText, True, black, white)
        self.Active = pygame.image.load("ASET/Active.png")
        self.NotActive = pygame.image.load("ASET/NotActive.png")

        self.text1Rec = self.text1.get_rect()
        self.text2Rec = self.text2.get_rect()
        self.text3Rec = self.text3.get_rect()
        self.text4Rec = self.text4.get_rect()
        self.text5Rec = self.text5.get_rect()
        self.ActiveRec = self.Active.get_rect()
        self.NotActiveRec = self.NotActive.get_rect()

        self.text1Rec.center = (50,400)
        self.text2Rec.center = (40,430)
        self.text3Rec.center = (40,460)
        self.text4Rec.center = (65,460)
        self.text5Rec.center = (100,430)
        self.ActiveRec.center = (120,430)
        self.NotActiveRec.center = (120,430)

    def resize(self):
        self.Active = pygame.transform.scale(self.Active,(20,60))
        self.NotActive = pygame.transform.scale(self.NotActive,(20,60))

    def render(self):
        screen.blit(self.text1, self.text1Rec)
        screen.blit(self.text2, self.text2Rec)
    def renderActive(self):
        screen.blit(self.Active, self.ActiveRec)
    def renderNotActive(self):
        screen.blit(self.NotActive, self.NotActiveRec)

class Util:

    def InsertPosisiKeMatrix(self, PositionX, PosititonY, StatusRotate, Size, ShipCount):
        isi = ''
        row = PositionX - Board1.boardCoor[0]
        col = PosititonY - Board1.boardCoor[1]
        row = row // 30
        col = col // 30
        horizontal = (Size[0] // 30)
        vertical = (Size[1] // 30)
        print("Horizontal: ", horizontal)
        print("Vertical : ", vertical)
        print("PositionX, PositionY")
        print(PositionX, PosititonY)
        print("Size: ", Size)
        print("Row,Col")
        print(row, col)
        print()
        if ShipCount == 0:
            isi = 'S'
        if ShipCount == 1:
            isi = 'D'
        if ShipCount == 2:
            isi = 'C'

        if not StatusRotate:
            for i in range(col, col + vertical):
                Board1.boardLogic[i][row] = isi

        if StatusRotate:
            for i in range(row, row + horizontal):
                Board1.boardLogic[col][i] = isi

    def CekOutOfBound(self, positionX, positionY, ShipCount, PosisiAwal, rotateStatus):
        print("Masuk sini")
        print("positionX: ", positionX)
        print("positionY: ", positionY)
        print("posisi awal: ", PosisiAwal)
        a, b = PosisiAwal
        x, y = PosisiAwal
        posAkhirX = positionX - x
        posAkhirY = positionY - y
        print(a, b)

        if positionY >= 330 or positionY <= 30 or positionX >= 360 or positionX <= 60:
            print("masuk sini")

            print(posAkhirX, posAkhirY)
            print("x,y", x, y)
            if ShipCount == 0:
                Ship.ship1Rec.move_ip(-posAkhirX, -posAkhirY)

            if ShipCount == 1:
                Ship.ship3Rec.move_ip(-posAkhirX, -posAkhirY)

            if ShipCount == 2:
                Ship.ship5Rec.move_ip(-posAkhirX, -posAkhirY)

        elif Ship.ship1Rec.colliderect(Ship.ship3Rec) or Ship.ship1Rec.colliderect(
                Ship.ship5Rec) or Ship.ship3Rec.colliderect(Ship.ship1Rec) or Ship.ship3Rec.colliderect(
                Ship.ship5Rec) or Ship.ship5Rec.colliderect(Ship.ship1Rec) or Ship.ship5Rec.colliderect(Ship.ship3Rec):
            if ShipCount == 0:
                Ship.ship1Rec.move_ip(-posAkhirX, -posAkhirY)

            if ShipCount == 1:
                Ship.ship3Rec.move_ip(-posAkhirX, -posAkhirY)

            if ShipCount == 2:
                Ship.ship5Rec.move_ip(-posAkhirX, -posAkhirY)

        # elif ShipCount == 0 :
        #     if Ship.ship1Rec.colliderect(Ship.ship3Rec) or Ship.ship1Rec.colliderect(Ship.ship5Rec):
        #         Ship.ship1Rec.move_ip(-posAkhirX, -posAkhirY)
        # elif ShipCount == 1 :
        #     if Ship.ship3Rec.colliderect(Ship.ship1Rec) or Ship.ship3Rec.colliderect(Ship.ship5Rec):
        #         Ship.ship3Rec.move_ip(-posAkhirX, -posAkhirY)
        # elif ShipCount == 2 :
        #     if Ship.ship5Rec.colliderect(Ship.ship1Rec) or Ship.ship5Rec.colliderect(Ship.ship3Rec):
        #         Ship.ship5Rec.move_ip(-posAkhirX, -posAkhirY)
        else:
            if ShipCount == 0:
                x, y = Ship.ship1Rec.x, Ship.ship1Rec.y
                if not rotateStatus:
                    self.SnapToGrid(x, y, 0)
                else:
                    self.SnapToGridHorizontal(x, y, 0)
            if ShipCount == 1:
                x, y = Ship.ship3Rec.x, Ship.ship3Rec.y
                if not rotateStatus:
                    self.SnapToGrid(x, y, 1)
                else:
                    self.SnapToGridHorizontal(x, y, 1)
            if ShipCount == 2:
                x, y = Ship.ship5Rec.x, Ship.ship5Rec.y
                if not rotateStatus:
                    self.SnapToGrid(x, y, 2)
                else:
                    self.SnapToGridHorizontal(x, y, 2)

    def SnapToGridHorizontal(self, positionX, positionY, ShipCount):
        x = 0
        y = 0
        if ShipCount == 0:
            if 45 <= positionX <= 375 and 15 <= positionY <= 345:
                temp1 = positionX % 30
                temp2 = positionY % 30

                Ship.ship1Rec.move_ip(-temp1 - x, -temp2 - y)

        elif ShipCount == 1:
            if 30 <= positionX <= 375 and 30 <= positionY <= 330:
                temp1 = positionX % 30
                temp2 = positionY % 30

                Ship.ship3Rec.move_ip(-temp1 - x, -temp2 - y)


        elif ShipCount == 2:
            if 30 <= positionX <= 360 and 15 <= positionY <= 330:
                temp1 = positionX % 30
                temp2 = positionY % 30

                Ship.ship5Rec.move_ip(-temp1 - x, -temp2 - y)

    def SnapToGrid(self, positionX, positionY, ShipCount):
        x = 0
        y = 0
        if ShipCount == 0:
            if 45 <= positionX <= 375 and 15 <= positionY <= 345:
                temp1 = positionX % 30
                temp2 = positionY % 30

                Ship.ship1Rec.move_ip(-temp1 - x, -temp2 - y)

        elif ShipCount == 1:
            if 30 <= positionX <= 375 and 30 <= positionY <= 330:
                temp1 = positionX % 30
                temp2 = positionY % 30

                Ship.ship3Rec.move_ip(-temp1 - x, -temp2 - y)


        elif ShipCount == 2:
            if 30 <= positionX <= 360 and 15 <= positionY <= 330:
                temp1 = positionX % 30
                temp2 = positionY % 30

                Ship.ship5Rec.move_ip(-temp1 - x, -temp2 - y)


class ship:
    def __init__(self):
        self.rotate3 = False
        self.rotate5 = False
        self.rotate1 = False
        # Load Kapal
        self.ship1 = pygame.image.load("ASET/ship1.png")
        self.ship3 = pygame.image.load("ASET/ship3.png")
        self.ship5 = pygame.image.load("ASET/ship5.png")
        self.ship1Rotate = pygame.transform.rotate(self.ship1, 90)
        self.ship3Rotate = pygame.transform.rotate(self.ship3, 90)
        self.ship5Rotate = pygame.transform.rotate(self.ship5, 90)
        self.ship1Rotate.convert()
        self.ship3Rotate.convert()
        self.ship5Rotate.convert()
        self.ship1.convert()
        self.ship3.convert()
        self.ship5.convert()

        self.Resize()

        # Make Rectangle
        self.ship1Rec = self.ship1.get_rect()
        self.ship3Rec = self.ship3.get_rect()
        self.ship5Rec = self.ship5.get_rect()

        # Set Position
        self.ship1Rec.center = 300, 450
        self.ship3Rec.center = 340, 450
        self.ship5Rec.center = 380, 450

    def Resize(self):
        # Resize Kapal
        self.ship1 = pygame.transform.scale(self.ship1, (30, 30))
        self.ship3 = pygame.transform.scale(self.ship3, (30, 90))
        self.ship5 = pygame.transform.scale(self.ship5, (30, 150))

    def load(self):
        screen.blit(self.ship1, self.ship1Rec)
        screen.blit(self.ship3, self.ship3Rec)
        screen.blit(self.ship5, self.ship5Rec)

    def rotate(self, ShipCount):
        if ShipCount == 1:
            self.ship3 = pygame.transform.rotate(self.ship3, 90)
            self.ship3Rec = self.ship3.get_rect()
            if not self.rotate3:
                self.rotate3 = True
            else:
                self.rotate3 = False
        elif ShipCount == 2:
            self.ship5 = pygame.transform.rotate(self.ship5, 90)
            self.ship5Rec = self.ship5.get_rect()
            if not self.rotate5:
                self.rotate5 = True
            else:
                self.rotate5 = False


class board:

    def __init__(self, pkaX, pkaY):
        self.square = pygame.image.load("ASET/square.png")
        self.squareHit = pygame.image.load("ASET/square hit.png")
        self.squareMapHit = pygame.image.load("ASET/square map hit.png")
        self.radar = pygame.image.load("ASET/squareRadar.png")

        self.list_kapal = {'C': 5, 'S': 1, 'D': 3}
        self.board = [[None for _ in range(10)] for _ in range(10)]
        self.boardLogic = [[None for _ in range(10)] for _ in range(10)]
        self.boardCoor = [pkaX, pkaY]
        self.resize()
        self.NoMissFlag = False
        self.energy = 0
        # self.text = font.render(self.energy,True,black,white)
        # self.textRec = self.text.get_rect()
        # self.textRec.center = 200,200

    def getEnergy(self):
        return self.energy
    def cekKalah(self):
        for items, keys in self.list_kapal.items():
            if keys > 0:
                return False
        return True

    def KenaRanjau(self):
        if self.board == Board1:
            Board2.AttackRanjau()
        elif self.board == Board2:
            Board1.AttackRanjau()

    def AttackRanjau(self):
        i = 0
        j = 0
        while i < 2:
            x = random.randint(0, 9)
            y = random.randint(0, 9)

            if self.boardLogic[x][y] is None:
                self.attack(x, y)
                i = i + 1
            if j > 95:
                break
            j = j + 1

    def PutRanjau(self, x, y):
        coorX = self.boardCoor[0]
        coorY = self.boardCoor[1]

        if x <= (30 * 10) + coorX and y <= (30 * 10) + coorY:
            x = x - coorX
            y = y - coorY
            indexHor = np.floor_divide(x, 30)
            indexVer = np.floor_divide(y, 30)
            if indexHor < 0 or indexVer < 0:
                return

            self.boardLogic[indexVer][indexHor] = 'R'

    def torpedo(self, row, column, axis):
        coorX = self.boardCoor[0]
        coorY = self.boardCoor[1]

        print("row,column awal: ", row, column)

        if row <= (30 * 10) + coorX and column <= (30 * 10) + y:
            row = row - coorX
            column = column - coorY
            row = np.floor_divide(row, 30)
            column = np.floor_divide(column, 30)
            print("row,column", row, column)
            if not axis:
                for i in range(10):
                    kena, _, overlap = self.attack(i, row)
                    if overlap:
                        pass
                    elif not kena:
                        self.board[row][i] = 0
                    elif kena:
                        self.board[row][i] = 1
            elif axis:
                for i in range(10):
                    kena, _, overlap = self.attack(column, i)
                    if overlap:
                        pass
                    elif not kena:
                        self.board[i][column] = 0
                    elif kena:
                        self.board[i][column] = 1

    def torpedoAI(self,row,column,axis):
        if not axis:
            for i in range(10):
                kena, _, overlap = self.attack(i, row)

                if overlap:
                    pass
                elif not kena:
                    self.board[row][i] = 0
                elif kena:
                    self.board[row][i] = 1
        elif axis:
            for i in range(10):
                kena,_,overlap = self.attack(column, i)

                if overlap:
                    pass
                elif not kena:
                    self.board[i][column] = 0
                elif kena:
                    self.board[i][column] = 1
    def attack(self, row, column):
        y = self.is_overlap(row, column)

        if y:
            return False, False, True
        elif not y:
            ApakahKena, tenggelamBool = self.is_hit(row, column)
            return ApakahKena, tenggelamBool, False

    def is_sink(self, Kapal):
        self.list_kapal.__setitem__(Kapal, self.list_kapal.get(Kapal) - 1)
        if self.list_kapal.get('C') == 0:
            self.list_kapal.__setitem__('C', self.list_kapal.get('C') - 1)
            print("Carrier is sink")
            return True
        elif self.list_kapal.get('D') == 0:
            self.list_kapal.__setitem__('D', self.list_kapal.get('D') - 1)
            print("Destroyer is sink")
            return True
        elif self.list_kapal.get('S') == 0:
            self.list_kapal.__setitem__('S', self.list_kapal.get('S') - 1)
            print("Submarine is sink")
            return True
        else:
            return False

    def is_overlap(self, row, column):
        if self.boardLogic[row][column] is not None and self.boardLogic[row][column] not in self.list_kapal:
            print("Kotak ini sudah di tembak")
            return True
        else:
            return False

    def is_hit(self, row, column):
        if self.boardLogic[row][column] in self.list_kapal:
            print("On Hit")
            self.NoMissFlag = True
            statusTenggelam = self.is_sink(self.boardLogic[row][column])
            self.boardLogic[row][column] = 'X'
            return True, statusTenggelam
        else:
            print("No Hit")
            self.NoMissFlag = False
            self.boardLogic[row][column] = 'O'
            return False, False


    def isi_board(self):
        ship_list = ['C', 'S', 'D']
        ship_size = [5, 1, 3]
        ship_listPicker = 0
        while True:
            if ship_listPicker >= len(ship_list):
                break
            count = 0
            isiKapalFlag = False
            row = random.randint(0, 7)
            column = random.randint(0, 7)
            axis = random.randint(0, 1)

            if axis == 0:
                if row + ship_size[ship_listPicker] - 1 < len(self.boardLogic):
                    for i in range(row, row + ship_size[ship_listPicker]):
                        count = count + 1
                        if self.boardLogic[i][column] is not None:
                            break
                    if count == ship_size[ship_listPicker]:
                        isiKapalFlag = True
                    if isiKapalFlag is True:

                        for i in range(row, row + ship_size[ship_listPicker]):
                            a = ship_list[ship_listPicker]
                            self.boardLogic[i][column] = str(a)
                        ship_listPicker += 1

            if axis == 1:
                if column + ship_size[ship_listPicker] - 1 < len(self.boardLogic):
                    for i in range(column, column + ship_size[ship_listPicker]):
                        count = count + 1
                        if self.boardLogic[row][i] is not None:
                            break

                    if count == ship_size[ship_listPicker]:
                        isiKapalFlag = True

                    if isiKapalFlag:

                        for i in range(column, column + ship_size[ship_listPicker]):
                            a = ship_list[ship_listPicker]
                            self.boardLogic[row][i] = str(a)
                        ship_listPicker += 1

    def render(self):
        x = self.boardCoor[0]
        y = self.boardCoor[1]
        for i in range(len(self.board)):

            for j in range(len(self.board)):
                if self.board[i][j] is None:
                    screen.blit(self.square, (x, y))
                elif self.board[i][j] == 1:
                    screen.blit(self.squareHit, (x, y))
                elif self.board[i][j] == 0:
                    screen.blit(self.squareMapHit, (x, y))
                y = y + 30
            x = x + 30
            y = self.boardCoor[1]

    def resize(self):
        self.square = pygame.transform.scale(self.square, (30, 30))
        self.squareHit = pygame.transform.scale(self.squareHit, (30, 30))
        self.squareMapHit = pygame.transform.scale(self.squareMapHit, (30, 30))

    def HitKotak(self, posX, posY):
        x = self.boardCoor[0]
        y = self.boardCoor[1]

        # Cek Apakah Masih Di dalam Grid
        if posX <= (30 * 10) + x and posY <= (30 * 10) + y:

            # Ganti warna
            posX = posX - x
            posY = posY - y
            indexHor = np.floor_divide(posX, 30)
            indexVer = np.floor_divide(posY, 30)
            if indexHor < 0 or indexVer < 0:
                return

            print(indexHor, indexVer)

            kena, tenggelam, overlap = self.attack(indexVer, indexHor)
            print(kena, tenggelam, overlap)

            if overlap:
                pass

            elif not kena:
                self.board[indexHor][indexVer] = 0

            elif kena:
                self.board[indexHor][indexVer] = 1

            pprint.pprint(self.boardLogic)

            return kena, tenggelam, overlap

            # if self.boardLogic[indexHor][indexVer] == None:
            #     self.board[indexHor][indexVer] = 0
            #     self.boardLogic[indexHor][indexVer] = 0
            # elif self.boardLogic[indexHor][indexVer] == 0:
            #     self.board[indexHor][indexVer] = 0
            # else:
            #     self.board[indexHor][indexVer] = 1

    def HitAI(self, row, column):
        print(row, column)
        kena, tenggelam, overlap = self.attack(column, row)

        print("row,column: ", row, column)
        if overlap:
            pass
        elif not kena:
            self.board[row][column] = 0
        elif kena:
            self.board[row][column] = 1

        return kena, tenggelam, overlap


class AI:
    def __init__(self):
        self.heuristicAI = np.array([[random.randint(1, 10) for _ in range(10)] for _ in range(10)])
        self.energy = Board2.energy
        self.angerMeter = 0
        self.MissCount = 0
    def pick(self, onHitStatus=False, sinkStatus=False):
        row, column = self.normalMove()
        return row, column

    def normalMove(self):
        x, y = np.unravel_index(self.heuristicAI.argmax(), self.heuristicAI.shape)
        self.heuristicAI[x][y] = -2000
        return y, x

    def TorpedoKah(self):
        if self.angerMeter > 3:
            if self.energy > 4:
                return True
        elif self.MissCount > 5:
            if self.energy > 4:
                return True
        else:
            return False

    def KeluarinSKillKah(self):
        axis = None
        x = 0
        y = 0
        skillwut = None
        if self.angerMeter > 3:

            if self.energy > 8:
                pass
                skillwut = 1
            if self.energy > 4:
                print("Energy lebih dari 4 dan anger meter lebih dari 3")
                x,y,axis = self.torpedo()
                skillwut = 0
                Board2.energy = Board2.energy - 4
                self.energy = Board2.energy
                self.angerMeter = 0

            return x,y,axis,skillwut


        if self.MissCount > 5 :
            if self.energy > 8:
                pass
                skillwut = 1
            if self.energy > 4:
                print("Energy lebih dari 4 dan misscount lebih dari 4")
                skillwut = 0
                Board2.energy = Board2.energy - 4
                self.energy = Board2.energy
                x,y,axis = self.torpedo()

            return x,y,axis,skillwut
        return None,None,None,None

    def nembak3x3(self):
        pass
            



    def torpedo(self):
        rowsum = np.sum(self.heuristicAI,axis=1)
        colsum = np.sum(self.heuristicAI,axis=0)

        rowMax = np.argmax(rowsum)
        NilairowMax = np.max(rowsum)
        colMax = np.argmax(colsum)
        NilaicolMax = np.max(colsum)

        if NilairowMax > NilaicolMax:
            for i in range(10):
                self.heuristicAI[rowMax][i] = -2000
            return 0, rowMax, True
        else:
            for i in range(10):
                self.heuristicAI[i][colMax] = -2000
            return colMax, 0, False
    def FokusKan(self, x, y):

        if x + 1 < len(self.heuristicAI):
            self.heuristicAI[y][x + 1] = 50
        if x - 1 >= 0:
            self.heuristicAI[y][x - 1] = 50
        if y + 1 < len(self.heuristicAI):
            self.heuristicAI[y + 1][x] = 50
        if y - 1 >= 0:
            self.heuristicAI[y - 1][x] = 50


# Game Loop

# Class and Function
Board1 = board(60, 30)
Board2 = board(420, 30)
Ship = ship()
ai = AI()

Board2.isi_board()
textrend = RenderText()

running = True
moving = False
KapalOnDrag = 10
util = Util()

simpan = 0
BukanSimpan = 0
GiliranPlayer = True

Start = True
testing = None
RanjauToggle = False
Play = True
ArahTembakTorpedo = False
TorpedoToggle = False
pprint.pp(Board1.boardLogic)

while running:

    # Screen Fill
    screen.fill((255, 255, 255))

    for event in pygame.event.get():
        keys = pygame.key.get_pressed()
        x, y = pygame.mouse.get_pos()
        # print(x, y)
        if event.type == pygame.QUIT:
            running = False

        if Start:

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_1:
                    print("Ship1: ")
                    print(Ship.ship1Rec.x, Ship.ship1Rec.y)
                    print("Ship3: ")
                    print(Ship.ship3Rec.x, Ship.ship3Rec.y)
                    print("Ship5: ")
                    print(Ship.ship5Rec.x, Ship.ship5Rec.y)
                if event.key == pygame.K_3:
                    print()
                    pprint.pprint(Board1.boardLogic)
            if event.type == pygame.MOUSEBUTTONDOWN and pygame.key.get_pressed()[pygame.K_LCTRL]:
                if Ship.ship3Rec.collidepoint(event.pos):
                    Ship.rotate(1)
                if Ship.ship5Rec.collidepoint(event.pos):
                    Ship.rotate(2)

            if event.type == pygame.MOUSEBUTTONDOWN and Start:
                if tombolStartRec.collidepoint(event.pos):
                    Start = False
                    tombolStart.fill((255, 255, 255, 0))
                    util.InsertPosisiKeMatrix(Ship.ship1Rec.x, Ship.ship1Rec.y, Ship.rotate1, Ship.ship1Rec.size, 0)
                    util.InsertPosisiKeMatrix(Ship.ship3Rec.x, Ship.ship3Rec.y, Ship.rotate3, Ship.ship3Rec.size, 1)
                    util.InsertPosisiKeMatrix(Ship.ship5Rec.x, Ship.ship5Rec.y, Ship.rotate5, Ship.ship5Rec.size, 2)
                if Ship.ship1Rec.collidepoint(event.pos):
                    moving = True
                    KapalOnDrag = 1
                    simpan = Ship.ship1Rec.x, Ship.ship1Rec.y
                elif Ship.ship3Rec.collidepoint(event.pos):
                    moving = True
                    KapalOnDrag = 2
                    simpan = Ship.ship3Rec.x, Ship.ship3Rec.y
                elif Ship.ship5Rec.collidepoint(event.pos):
                    moving = True
                    KapalOnDrag = 3
                    simpan = Ship.ship5Rec.x, Ship.ship5Rec.y

            if event.type == pygame.MOUSEBUTTONUP and moving is True:
                moving = False

                if KapalOnDrag == 1:
                    x, y = Ship.ship1Rec.x, Ship.ship1Rec.y
                    util.CekOutOfBound(x, y, 0, simpan, Ship.rotate1)

                if KapalOnDrag == 2:
                    x, y = Ship.ship3Rec.x, Ship.ship3Rec.y
                    print("siman: ", simpan)
                    util.CekOutOfBound(x, y, 1, simpan, Ship.rotate3)

                if KapalOnDrag == 3:
                    x, y = Ship.ship5Rec.x, Ship.ship5Rec.y
                    util.CekOutOfBound(x, y, 2, simpan, Ship.rotate5)

            if event.type == pygame.MOUSEMOTION and moving:
                if KapalOnDrag == 1:
                    Ship.ship1Rec.move_ip(event.rel)
                elif KapalOnDrag == 2:
                    Ship.ship3Rec.move_ip(event.rel)
                elif KapalOnDrag == 3:
                    Ship.ship5Rec.move_ip(event.rel)

        if not Start and Play:
            EnergyCountText = chr(Board1.energy)
            ai.energy = Board2.energy

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_1 and Board1.energy >= 8:
                    if TorpedoToggle == True:
                        TorpedoToggle = False
                    elif TorpedoToggle == False:
                        TorpedoToggle = True
                if event.key == pygame.K_c:
                    if ArahTembakTorpedo == False:
                        ArahTembakTorpedo = True
                    elif ArahTembakTorpedo == True:
                        ArahTembakTorpedo = False
                if event.key == pygame.K_2:
                    print(2)
                    pprint.pprint(Board2.boardLogic)

                if event.key == pygame.K_4:
                    x,y,axis = ai.torpedo()
                    Board1.torpedoAI(x,y,axis)

                if event.key == pygame.K_5:
                    print()
                    pprint.pprint(ai.heuristicAI)
                    print()
                    pprint.pprint(Board1.boardLogic)
                if event.key == pygame.K_3:
                    print("ENERGY")
                    print(Board1.energy, Board2.energy)
                    print("Torpedo Toggle")
                    print(TorpedoToggle)

            if GiliranPlayer:
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if TorpedoToggle:
                        Board1.energy = Board1.energy - 8
                        Board2.torpedo(x, y, ArahTembakTorpedo)
                        TorpedoToggle = False
                    else:
                        testing = Board2.HitKotak(x, y)
                        print(testing)

                    if testing is not None:
                        if testing[2]:
                            GiliranPlayer = True
                        elif testing[0]:
                            Board2.energy = Board2.energy + 2
                            ai.angerMeter += 1
                        elif not testing[0]:
                            Board1.energy = Board1.energy + 1
                            GiliranPlayer = False
            if not GiliranPlayer:
            # ba = ai.TorpedoKah()

            # if ba:
            #     x,y,axis = ai.torpedo()
            #     Board1.torpedoAI(x,y,axis)
            #     Board2.energy = Board2.energy - 4
            #     ai.energy = Board2.energy

            # if not ba:
                if testing is None:
                    x, y = ai.pick()
                    testing = Board1.HitAI(x, y)
                elif testing is not None:
                    x, y = ai.pick(testing[0], testing[1])
                    testing = Board1.HitAI(x, y)
                if testing[2]:
                    GiliranPlayer = False
                elif testing[0]:
                    Board1.energy = Board1.energy + 2
                    ai.FokusKan(x, y)
                    ai.MissCount = 0
                    ai.angerMeter = 0
                elif not testing[0]:
                    Board2.energy = Board2.energy + 1
                    ai.MissCount = ai.MissCount + 1
                    GiliranPlayer = True


    if Board1.cekKalah():
        Play = False
        screen.blit(text, textRec)
    elif Board2.cekKalah():
        Play = False
        screen.blit(text2, text2Rec)




    if not Start:
        textrend.renderNotActive()
        textrend.render()
        Board2.render()

    if Board1.energy >= 8:
        textrend.renderActive()
    Ship.load()
    Board1.render()
    screen.blit(tombolStart, tombolStartRec)

    pygame.display.update()
