import pprint

import pygame
import numpy as np
import random

pygame.init()

screen = pygame.display.set_mode((800, 600))
pygame.display.set_caption("Battleship")


class Util:

    def CekOutOfBound(self, positionX, positionY):
        pass

    def SnapToGridHorizontal(self, positionX, positionY, ShipCount):
        pass
    def SnapToGrid(self, positionX, positionY, ShipCount):
        x = 0
        y = 0
        if ShipCount == 0:
            if 45 <= positionX <= 375 and 15 <= positionY <= 345:
                temp1 = positionX % 30
                temp2 = positionY % 30

                posXBaru = positionX - temp1
                posYBaru = positionY - temp2

                tempX = posXBaru + 15
                tempY = posYBaru + 15

                if tempX <= 60:
                    x = -30
                if tempY <= 30:
                    y = -30
                if tempX >= 360:
                    x = 30
                if tempY >= 330:
                    y = 30

                Ship.ship1Rec.move_ip(-temp1 + 15 - x,-temp2 + 15 - y)

        elif ShipCount == 1:
            if 45 <= positionX <= 375 and 15 <= positionY <= 345:
                temp1 = positionX % 30
                temp2 = positionY % 30

                posXBaru = positionX - temp1
                posYBaru = positionY - temp2

                tempX = posXBaru + 15
                tempY = posYBaru + 15

                if tempX <= 60:
                    x = -30
                if tempX >= 360:
                    x = 30
                if tempY >=300:
                    y = 30
                if tempY <= 60:
                    y = -30





                Ship.ship3Rec.move_ip(-temp1 + 15 - x, -temp2 + 15 - y)


        elif ShipCount == 2:
            if 60 <= positionX <= 360 and 30 <= positionY <= 330:
                temp1 = positionX % 30
                temp2 = positionY % 30

                posXBaru = positionX - temp1
                posYBaru = positionY - temp2

                tempX = posXBaru + 15
                tempY = posYBaru + 15

                if tempX <= 60:
                    x = -30
                if tempX >= 360:
                    x = 30

                if tempY >=300:
                    y = 30
                if tempY <= 90:
                    y = -30
                if tempY >= 270:
                    y = 30


                Ship.ship5Rec.move_ip(-temp1 + 15 - x, -temp2 + 15 - y)


class ship:
    def __init__(self):
        # Load Kapal
        self.ship1 = pygame.image.load("ASET/ship1.png")
        self.ship3 = pygame.image.load("ASET/ship3.png")
        self.ship5 = pygame.image.load("ASET/ship5.png")
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

            self.board[indexHor][indexVer] = 1


# Game Loop
running = True
moving = False
KapalOnDrag = 10
util = Util()
Board1 = board(60, 30)
Board2 = board(420, 30)

Ship = ship()
Board1.isi_board()
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


        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_1:
                print("Ship1: ")
                print(Ship.ship1Rec.center)
                print("Ship3: ")
                print(Ship.ship3Rec.center)
                print("Ship5: ")
                print(Ship.ship5Rec.center)

        if event.type == pygame.MOUSEBUTTONDOWN:
            if Ship.ship1Rec.collidepoint(event.pos):
                moving = True
                KapalOnDrag = 1
            elif Ship.ship3Rec.collidepoint(event.pos):
                moving = True
                KapalOnDrag = 2
            elif Ship.ship5Rec.collidepoint(event.pos):
                moving = True
                KapalOnDrag = 3

        if event.type == pygame.MOUSEBUTTONUP:
            moving = False
            x,y = Ship.ship1Rec.center
            util.SnapToGrid(x,y, 0)
            x,y = Ship.ship3Rec.center
            util.SnapToGrid(x,y, 1)
            x,y = Ship.ship5Rec.center
            util.SnapToGrid(x,y, 2)

        if event.type == pygame.MOUSEMOTION and moving:
            if KapalOnDrag == 1:
                Ship.ship1Rec.move_ip(event.rel)
                print(Ship.ship1Rec.center)
            elif KapalOnDrag == 2:
                Ship.ship3Rec.move_ip(event.rel)
                print(Ship.ship3Rec.center)
            elif KapalOnDrag == 3:
                Ship.ship5Rec.move_ip(event.rel)
                print(Ship.ship5Rec.center)

    Ship.load()
    Board1.render()
    # Board2.render()

    pygame.display.update()
