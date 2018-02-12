import random
import sys
import numpy as np
import math
from time import time

n = 8  # board size (even)
b_ord = [['.' for x in range(n + 1)] for y in range(n + 1)]
strt_time = None
# Maxdep=12
makeboard='BEXEEOEEEEEXEOEEEEEEXOEEEEEEOOOEEEEEOOXXXEEOEEEXEEEEEOOOOEEEEEEEE'
# makeboard1=makeboard[1:]
#time_allowed = int(sys.argv[2])
time_allowed = 2

#makeboard = sys.argv[1]


# makeboard=makeboard[1:]
def board(makeboard):
    for i in range(1, 65):

        if makeboard[i] == 'E':
            c = '.'
        elif makeboard[i] == 'O':
            c = 'O'
        elif makeboard[i] == 'X':
            c = 'X'
        columns = ((i - 1) % 8)
        rows = (i - 1) % 8
        b_ord[columns][rows] = c
    return b_ord


def resetboard():
    if n % 2 == 0:  # if board size is even
        s = (n - 2) / 2
        b_ord[s][s] = 'O'
        b_ord[n - 1 - s][s] = 'X'
        b_ord[s][n - 1 - s] = 'X'
        b_ord[n - 1 - s][n - 1 - s] = 'O'


#### visualization of board
def fullboard():
    m = len(str(n - 1))
    for y in range(8):
        row = ''
        for x in range(8):
            row += b_ord[x][y]
            row += ' ' * m
        print(row + ' ' + str(y))
    print
    row = ''
    for x in range(8):
        row += str(x).zfill(m) + ' '
    print(row + '\n')


def isOnBoard(x, y):
    # Returns True if coordinates inside board.
    return x >= 0 and x <= 7 and y >= 0 and y <= 7


def valid_move(b_ord, symbol, xstart, ystart):
    # Returns False if the player's move invalid.
    # If it is a valid move, returns a list of spaces that would become the player's if they made a move here.
    if b_ord[xstart][ystart] != '.' or not isOnBoard(xstart, ystart):
        return False

    b_ord[xstart][ystart] = symbol  # temporarily set the tile on the board.
    # print('yes')
    if symbol == 'X':
        opp_symbol = 'O'
    else:
        opp_symbol = 'X'  ###########

    fliptiles = []
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        x, y = xstart, ystart
        x += xdir
        y += ydir
        # print(x)
        # print(y)
        if isOnBoard(x, y) and b_ord[x][y] == opp_symbol:
            # There is a piece belonging to the other player next to our piece.
            x += xdir
            y += ydir
            # print(x)
            # print(y)
            if not isOnBoard(x, y):
                continue
            while b_ord[x][y] == opp_symbol:
                x += xdir
                y += ydir
                # print(x)
                # print(y)
                if not isOnBoard(x, y):  # break out of while loop, then continue in for loop
                    break
            if not isOnBoard(x, y):
                continue
            if b_ord[x][y] == symbol:
                # There are pieces to flip over. Go in the reverse direction until we reach the original space, noting all the tiles along the way.
                while True:
                    x -= xdir
                    y -= ydir
                    if x == xstart and y == ystart:
                        break
                    fliptiles.append([x, y])

    b_ord[xstart][ystart] = '.'  # restore the empty space
    if len(fliptiles) == 0:
        #	print 'pass' # If no tiles were flipped, this is not a valid move.
        return False
    # print (fliptiles)
    return fliptiles


def getbestmoves(b_ord, symbol):
    # Returns a list of [x,y] lists of valid moves for the given player on the given board.
    bestmoves = []

    for x in range(8):
        for y in range(8):
            if valid_move(b_ord, symbol, x, y) != False:
                bestmoves.append([x, y])
                # print(bestmoves)

    return bestmoves


def makemove(b_ord, symbol, xstart, ystart):
    # Place the tile  and flip any of the opponent's pieces.
    # Returns False if this is an invalid move, True if it is valid.
    fliptiles = valid_move(b_ord, symbol, xstart, ystart)

    if fliptiles == False:
        print("pass")
        return False

    b_ord[xstart][ystart] = symbol
    for x, y in fliptiles:
        b_ord[x][y] = symbol
    return True


def firstplayer():
    # Randomly choose the player who goes first.
    if random.randint(0, 1) == 0:
        return 'computer'
    else:
        return 'player'


def iscorner(x, y):
    return (x == 0 and y == 0) or (x == 7 and y == 0) or (x == 0 and y == 7) or (x == 7 and y == 7)


def playersymbol(symbol):
    if symbol == 'W':
        return ['X', 'O']
    else:
        return ['O', 'X']


def EvalBoard(board, player):
    total = 0
    for x in range(8):
        for y in range(8):
            if board[x][y] == player:
                if (x == 0 or x == n - 1) and (y == 0 or y == n - 1):
                    total += 10  # corner
                elif (x == 1 or x == n - 2) and (y == 0 or y == 7):
                    total += 6
                elif (x == 0 or x == n - 1) and (y == 1 or y == 6):
                    total += 6
                elif (x == 1 or x == n - 2) and (y == 1 or y == 6):
                    total += -5
                elif (x == 2 or x == n - 3) or (y == 0 or y == n - 1):
                    total += 8  # side
                elif (x == 3 or x == n - 4) or (y == 0 or y == n - 1):
                    total += 8  # side
                elif (x == 0 or x == n - 1) or (y == 2 or y == n - 3):
                    total += 8  # side
                elif (x == 0 or x == n - 1) or (y == 3 or y == n - 4):
                    total += 8  # side
                else:
                    total += 1
    return total


def Score(b_ord):
    # Determine the score by counting the tiles. Returns a dictionary with keys 'X' and 'O'.
    xscore = 0
    oscore = 0
    for x in range(8):
        for y in range(8):
            if b_ord[x][y] == 'X':
                xscore += 1
            if b_ord[x][y] == 'O':
                oscore += 1
    return xscore - oscore


def copyboard(b_ord1):
    # Make a duplicate of the board list and return the duplicate.
    falseboard = [['.' for x in range(n)] for y in range(n)]

    for x in range(8):
        for y in range(8):
            falseboard[x][y] = b_ord1[x][y]

    return falseboard


#
# def playermove(b_ord, playersymb):
#     # Let the player type in their move.
#     # Returns the move as [x, y]
#     coordinates = ['0', '1', '2', '3', '4', '5', '6', '7']
#     brac = '('
#     brac1 = ')'
#     comma = ','
#     while True:
#         # print('Enter move in [x,y] format.')
#         move = raw_input().lower()
#
#         if len(move) == 5 and move[0] in brac and move[1] in coordinates and move[2] in comma and move[
#             3] in coordinates and move[4] in brac1:
#             x = int(move[1])
#             y = int(move[3])
#             if valid_move(b_ord, playersymb, x, y) == False:
#                 continue
#             else:
#                 break
#         else:
#             print('Not Valid. Pls enter other move.')
#
#     return [x, y]


def alphabeta(state, dep, maxdep, alpha, beta, player):
    if player == +1:
        possibleMoves = getbestmoves(state, symbol1)

        if (dep >= maxdep):
            value = EvalBoard(state, symbol1)
            # print (dep)
            return (value)

        for x, y in possibleMoves:
            brd_cpy = copyboard(state)
            makemove(brd_cpy, symbol1, x, y)
            t = time()
            st = abs(t - global_t)
            if (st < time_allowed):

                val = alphabeta(brd_cpy, dep + 1, maxdep, alpha, beta, -player)

                # print (val)
                if val > alpha:
                    alpha = val

                    # print(best)
                    # print(alpha)
                if beta <= alpha:
                    break

        return alpha

    else:
        possibleMoves = getbestmoves(state, symbol2)
        # t = time()
        # st = abs(t - global_t)

        if (dep >= maxdep):
            value = EvalBoard(state, symbol2)
            # print (dep)
            return (value)

        for x, y in possibleMoves:
            brd_cpy = copyboard(state)
            makemove(brd_cpy, symbol2, x, y)
            t = time()
            st = abs(t - global_t)
            if (st < time_allowed):
                val = alphabeta(brd_cpy, dep + 1, maxdep, alpha, beta, player)  ## should change

                if val < beta:
                    beta = val

                if beta <= alpha:
                    break

        return beta


def algorithm(mainboard, computersymb):
    possiblemoves = getbestmoves(mainboard, computersymb)
    # print (possiblemoves)
    value = []
    move = []
    t = time()
    st = abs(t - global_t)
    while (st < time_allowed):
        Maxdepth = 0
        best = possiblemoves[0]
        if possiblemoves != []:
            for x, y in possiblemoves:
                brd_cpy = copyboard(mainboard)
                makemove(brd_cpy, computersymb, x, y)
                val = alphabeta(brd_cpy, 0, Maxdepth, -10000, 10000, 1)
                value.append(val)
                Maxdepth = Maxdepth + 1
                move.append([x, y])
                # print (val)
            # moveindex = value.index(max(value))
            # best = possiblemoves[moveindex]
        return (move.pop())


# def calculatedepth(time_allowed):
# branch = 15
# depth = 50
#
# nodenum = math.pow(branch, (3 * depth) / 4)
# # print(nodenum)
# nodetime = 7 / nodenum
# # print(nodetime)
# value = math.log((time_allowed / nodetime), 20)
# depthfin = (4 * value) / 3
# # print(depthfin)
# return math.floor(depthfin)


def end_move(b_ord, playersymb):
    # Returns a list of [x,y] lists of valid moves for the given player on the given board.

    for x in range(8):
        for y in range(8):
            if valid_move(b_ord, playersymb, x, y):
                return False
    return True


def points(playersymb, computersymb):
    # Prints out the current score.
    scores = Score(mainboard)
    # print('Player points %s . Computer points %s .' % (scores[playersymb], scores[computersymb]))


# mainboard=b_ord
# resetboard()
br = board(makeboard)
mainboard = br
# fullboard()
symbol = makeboard[0]

playersymb, computersymb = playersymbol(symbol)
symbol1 = 'O'
symbol2 = 'X'

p = getbestmoves(mainboard, computersymb)

if (end_move(mainboard, computersymb) == True) or (p == []):
    print
    'pass'
else:
    tm = time()
    global_t = tm

    # Maxdepth = calculatedepth(time_allowed)
    # Maxdepth = int(Maxdepth)
    # print (Maxdepth)

    x, y = algorithm(mainboard, computersymb)

    end_time = time()

    # print(abs(end_time - global_t))

    # timess(strt_time)
    # x = bestMove[0]
    # y = bestMove[1]
    # makemove(mainboard, playersymb, x, y)
    # fullboard()
    print('(%d,%d)' % (y + 1, x + 1))
