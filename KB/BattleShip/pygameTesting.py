import pygame
import random

# Initialize Pygame
pygame.init()

# Set up the display
WIDTH, HEIGHT = 800, 600
WIN = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Battleship Game")

# Set up colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)

# Set up the game variables
FPS = 60
SHIP_WIDTH, SHIP_HEIGHT = 50, 50
ENEMY_WIDTH, ENEMY_HEIGHT = 30, 30

ship_x = WIDTH // 2 - SHIP_WIDTH // 2
ship_y = HEIGHT - SHIP_HEIGHT - 10
enemy_x = random.randint(0, WIDTH - ENEMY_WIDTH)
enemy_y = random.randint(50, 150)

# Set up the game clock
clock = pygame.time.Clock()

# Game loop
running = True
while running:
    clock.tick(FPS)

    # Handle events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # Move the ship
    keys = pygame.key.get_pressed()
    if keys[pygame.K_LEFT] and ship_x > 0:
        ship_x -= 5
    if keys[pygame.K_RIGHT] and ship_x < WIDTH - SHIP_WIDTH:
        ship_x += 5

    # Move the enemy
    enemy_y += 2
    if enemy_y > HEIGHT:
        enemy_x = random.randint(0, WIDTH - ENEMY_WIDTH)
        enemy_y = random.randint(50, 150)

    # Check for collision
    if ship_x < enemy_x + ENEMY_WIDTH and \
       ship_x + SHIP_WIDTH > enemy_x and \
       ship_y < enemy_y + ENEMY_HEIGHT and \
       ship_y + SHIP_HEIGHT > enemy_y:
        running = False

    # Draw the game objects
    WIN.fill(WHITE)
    pygame.draw.rect(WIN, RED, (ship_x, ship_y, SHIP_WIDTH, SHIP_HEIGHT))
    pygame.draw.rect(WIN, BLACK, (enemy_x, enemy_y, ENEMY_WIDTH, ENEMY_HEIGHT))

    # Update the display
    pygame.display.update()

# Quit the game
pygame.quit()