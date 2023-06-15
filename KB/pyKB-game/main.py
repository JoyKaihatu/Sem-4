# Module Imports
import pygame

# Module Initialization
pygame.init()

# Game Assets and Objects
class Ship:
    def __init__(self, name,img, pos, size, numGuns=0, gunPath=None,gunCoordsOffset=None):
        self.name = name
        # Load vertical Image
        self.vImage = loadImage(img)
        self.vImageWidth = self.vImage.get_width()
        self.vImageHeight = self.vImage.get_height()
        self.vImageRect = self.vImage.get_rect()
        self.vImageRect.topleft = pos
        # Load Horizontal Image
        self.hImage = pygame.transform.rotate(self.vImage,-90)
        self.hImageWidth = self.hImage.get_width()
        self.hImageHeight = self.hImage.get_height()
        self.hImageRect = self.hImage.get_rect()
        self.hImageRect.topleft = pos
        # Image and Rectangle
        self.image = self.vImage
        self.rect = self.vImageRect
        self.rotation = False



    def draw(self, window):
        """Draw the ship to the screen"""
        window.blit(self.image, self.rect)

# Game Utility Functions
def createGameGrid(rows,cols,cellsize,pos):
    """Create a game grid with coordinate for each cell"""
    startX = pos[0]
    startY = pos[1]
    coorGrid = []
    for row in range(rows):
        rowX = []
        for col in range(cols):
            rowX.append((startX, startY))
            startX += cellsize
        coorGrid.append(rowX)
        startX = pos[0]
        startY += cellsize
    return coorGrid


def updateGameLogic(rows, cols):
    """Update the game grid with logic, ie - spaces and x for ships"""
    gameLogic = []
    for row in range(rows):
        rowX = []
        for col in range(cols):
            rowX.append(" ")
        gameLogic.append(rowX)
    return gameLogic


def showGridOnScreen(window, cellsize, playerGrid, computerGrid):
    """Draws the player and computer grids to the screen"""
    gamegrids = [playerGrid, computerGrid]
    for grid in gamegrids:
        for row in grid:
            for col in row:
                pygame.draw.rect(window, (255,255,255), (col[0], col[1], cellsize, cellsize),1)


def printGameLogic():
    """prints to the terimnal the game logic"""
    print('Player Grid'.center(50, '#'))
    for _ in pGameLogic:
        print(_)
    print('Computer Grid'.center(50, '#'))
    for _ in cGameLogic:
        print(_)

def loadImage(path, size, rotate=False):
    """A function to import images into memory"""
    img = pygame.image.load(path).convert_alpha()
    img = pygame.transform.scale(img, size)
    if rotate == True:
        img = pygame.transform.rotate(img, -90)
    return img

def updateGameScreen(window):
    window.fill((0,0,0))

    # Draw Grid
    showGridOnScreen(window,CELLSIZE, pGameGrid, cGameGrid)

    # Draw Ships


    pygame.display.update()




# Game Settings and Variables
SCREENWIDTH = 800
SCREENHEIGHT = 600
ROWS = 10
COLS = 10
CELLSIZE = 30

# Colors


# Pygame displays Initialization
GAMESCREEN = pygame.display.set_mode((SCREENWIDTH, SCREENHEIGHT))
pygame.display.set_caption('Battle Ship')

# Game Lists/Dictionaries
PLAYERFLEET = {
    'battleship': ['battleship', "assets/images/ships/battleship/battleship.png", (125,600), (40,195),
                   4, 'assets/images/ships/battleshipgun.png', [-0.525,-0.34, 0.67, 0.49]],
    'cruiser': ['cruiser','assets/images/ships/cruser/cruiser.png', (200,600), (40,195),
                2, 'assets/iamges/ships/cruiser/cruisergun.png', (0.4,0.125), [-0.36,0.64]]

}

# Loading Game Variables
pGameGrid = createGameGrid(ROWS,COLS,CELLSIZE,(50,50))
pGameLogic = updateGameLogic(ROWS,COLS)


cGameGrid = createGameGrid(ROWS,COLS, CELLSIZE,(SCREENWIDTH - (ROWS * CELLSIZE) - 50, 50))
cGameLogic = updateGameLogic(ROWS,COLS)


printGameLogic()
# Loading Game Sounds


# Initialize Players


# Main Game Loop
RUNGAME = True
while RUNGAME:

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            RUNGAME = False

    updateGameScreen(GAMESCREEN)

pygame.quit()
