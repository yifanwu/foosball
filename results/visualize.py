import pygame
pygame.init()
ROWS = 9
SCREEN_W = 1000
SCREEN_H = 620
VSCALE = 1400.0
NUM_FOOSPLAYERS = 26
NUM_FIELDED = 22
window = pygame.display.set_mode((SCREEN_W, SCREEN_H)) 

def redraw(game_state): 
  me_per_row = [len(filter(lambda x: x==i, game_state[4:30])) for i in range(-4,5)]
  opp_per_row = [len(filter(lambda x: x==i, game_state[56:82])) for i in range(-4,5)]
  me_fat = [[game_state[i] for i in range(30,56) if game_state[i-26]==x] for x in range(-4,5)]
  opp_fat = [[game_state[i] for i in range(82,108) if game_state[i-26]==x] for x in range(-4,5)]

  # Each row is 100 pix wide, 20 buffer on each side
  pygame.draw.rect(window, pygame.Color("black"), pygame.Rect(0,0,SCREEN_W,SCREEN_H))
  pygame.draw.circle(window, pygame.Color("red"), (100*(4+game_state[3]) + 50, SCREEN_H - 50) , 20)
  for i in range(ROWS):
    ht = int(VSCALE*float(me_per_row[i])/float(NUM_FOOSPLAYERS))
    pygame.draw.rect(window,pygame.Color("green"), pygame.Rect(100*i + 20, SCREEN_H-90-ht, 27, ht))
    ht = int(VSCALE*float(opp_per_row[i])/float(NUM_FOOSPLAYERS))
    pygame.draw.rect(window,pygame.Color("blue"), pygame.Rect(100*i + 53, SCREEN_H-90-ht, 27, ht))
    fat = int(VSCALE*sum(0.99**x for x in me_fat[i])/float(NUM_FOOSPLAYERS))
    pygame.draw.rect(window,pygame.Color("red"),pygame.Rect(100*i + 30, SCREEN_H-90-fat, 7, fat))
    fat = int(VSCALE*sum(0.99**x for x in opp_fat[i])/float(NUM_FOOSPLAYERS))
    pygame.draw.rect(window,pygame.Color("red"),pygame.Rect(100*i + 63, SCREEN_H-90-fat, 7, fat))
  myfont = pygame.font.SysFont("monospace", 22)
  label = myfont.render(str(game_state[2]), 1, (255,255,0))
  window.blit(label, (SCREEN_W-160, 50))
  label = myfont.render("Score: " + str(game_state[0]), 1, pygame.Color("green"))
  window.blit(label, (SCREEN_W-240, 100))
  label = myfont.render("Score: " + str(game_state[1]), 1, pygame.Color("blue"))
  window.blit(label, (SCREEN_W-240, 150))
  pygame.display.flip()
