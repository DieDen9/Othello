import sys
from time import time


def board(makeboard):
    for i in range(1, 65):

        if makeboard[i] == 'E':
            c = '.'
        elif makeboard[i] == 'O':
            c = 'O'
        elif makeboard[i] == 'X':
            c = 'X'
        columns = int(((i - 1) % 8))
        rows = int((i - 1) / 8)
        b_ord[columns][rows] = c
    return b_ord


# visualization of board
def print_board():
    m = len(str(8 - 1))
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


def isValid(b_ord, symbol, xstart, ystart):
    # Returns False if the player's move invalid.
    # If it is a valid move, returns a list of spaces that would become the player's if they made a move here.
    if b_ord[xstart][ystart] != '.' or not isOnBoard(xstart, ystart):
        return False

    b_ord[xstart][ystart] = symbol  # temporarily set the tile on the board.
    # print('yes')
    if symbol == 'X':
        opp_symbol = 'O'
    else:
        opp_symbol = 'X'

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
        return False
    return fliptiles


def getMoves(b_ord, symbol):
    # Returns a list of [x,y] lists of valid moves for the given player on the given board.
    moves = []

    for x in range(8):
        for y in range(8):
            if isValid(b_ord, symbol, x, y) != False:
                moves.append([x, y])
                # print(bestmoves)

    return moves


def makemove(brd, player, xstart, ystart):
    # Place the tile  and flip any of the opponent's pieces.
    # Returns False if this is an invalid move, True if it is valid.
    fliptiles = isValid(brd, player, xstart, ystart)

    if fliptiles == False:
        print("pass")
        return False

    brd[xstart][ystart] = player
    for x, y in fliptiles:
        brd[x][y] = player
    return True


def cloneBoard(board):

    copy = [['.' for r in range(8)] for c in range(8)]
    for i in range(8):
        for j in range(8):
            copy[i][j] = board[i][j]

    return copy

def getPly(pl):
    if pl == 'W':
        return ['X', 'O']
    else:
        return ['O', 'X']


def gameEvaluater(board, player):
    score = 0
    n = 8
    for i in range(8):
        for j in range(8):
            if board[i][j] == player:
                # bad regions to place into
                if (i == 1 or i == n - 2) and (j == 1 or j == 6):
                    score += -5
                # corners aka. key regions to win the game
                elif (i == 0 or i == n - 1) and (j == 0 or j == n - 1):
                    score += 10
                # good regions
                elif (i == 1 or i == n - 2) and (j == 0 or j == 7):
                    score += 6
                elif (i == 0 or i == n - 1) and (j == 1 or j == 6):
                    score += 6
                # second best regions.
                elif (i == 2 or i == n - 3) or (j == 0 or j == n - 1):
                    score += 8
                elif (i == 3 or i == n - 4) or (j == 0 or j == n - 1):
                    score += 8
                elif (i == 0 or i == n - 1) or (j == 2 or j == n - 3):
                    score += 8
                elif (i == 0 or i == n - 1) or (j == 3 or j == n - 4):
                    score += 8
                # neutral
                else:
                    score += 1
    # print(score)
    return score




def alphabeta(pos, depth, maxDepth, alpha, beta, turn):
    if turn == +1:
        possibleMoves = getMoves(pos, White_Player)

        if (depth >= maxDepth):
            value = gameEvaluater(pos, White_Player)
            # print (dep)
            return (value)

        for x, y in possibleMoves:
            b = cloneBoard(pos)
            makemove(b, White_Player, x, y)
            tCurrent = time()
            timeThreshold = abs(tCurrent - roundStartTime)
            if (timeThreshold < tLimit):

                val = alphabeta(b, depth + 1, maxDepth, alpha, beta, -turn)

                # print (val)
                if val > alpha:
                    alpha = val

                    # print(best)
                    # print(alpha)
                if beta <= alpha:
                    break

        return alpha

    else:
        possibleMoves = getMoves(pos, Black_Player)
        if (depth >= maxDepth):
            value = gameEvaluater(pos, Black_Player)
            return value

        for x, y in possibleMoves:
            brd_cpy = cloneBoard(pos)
            makemove(brd_cpy, Black_Player, x, y)
            tCurrent = time()
            timeThreshold = abs(tCurrent - roundStartTime)
            if (timeThreshold < tLimit):
                value = alphabeta(brd_cpy, depth + 1, maxDepth, alpha, beta, turn)

                if value < beta:
                    beta = value

                if beta <= alpha:
                    break

        return beta

def boardCounter(board):  # done
    # Determine the score by counting the tiles. Returns a dictionary with keys 'X' and 'O'.
    bScore = 0
    wScore = 0
    for x in range(8):
        for y in range(8):
            if board[x][y] == 'X':
                bScore += 1
            if board[x][y] == 'O':
                wScore += 1
    return bScore - wScore


def play(board, computersymb):
    possiblemoves = getMoves(board, computersymb)
    # print (possiblemoves)
    action = []
    value = []

    tStart = time()
    timeThrehold = abs(tStart - roundStartTime) #print(tStart - roundStartTime)
    while (timeThrehold < tLimit):
        Maxdepth = 12 # setting max depth to avoid extensive computing of the result
        best = possiblemoves[0]
        if possiblemoves != []:
            for x, y in possiblemoves:
                copy = cloneBoard(board)
                makemove(copy, computersymb, x, y)
                score = alphabeta(copy, 0, Maxdepth, -10000, 10000, 1)
                value.append(score)
                #Maxdepth = Maxdepth + 1
                action.append([x, y])

        return (action.pop())


def isFinished(b_ord, playersymb):
    # Returns true if no more plys left to play
    for x in range(8):
        for y in range(8):
            if isValid(b_ord, playersymb, x, y):
                return False
    return True


def main():
    global board_size, tLimit, playerTurn, mainboard, roundStartTime, b_ord, White_Player, Black_Player
    board_size = 8  # board_size = 9
    b_ord = [['.' for x in range(board_size + 1)] for y in range(board_size + 1)]
    #print(type(sys.argv[2]))
    if len(sys.argv) < 2:
        print("Error! input arguments must be in the following order: \n "
              "Othello.py board time_limit")
        return
    elif int(sys.argv[2]) <= 0:
        print("Time limit must be positive integer!")
        return

    # input_board='BEXEEOEEEEEXEOEEEEEEXOEEEEEEOOOEEEEEOOXXXEEOEEEXEEEEEOOOOEEEEEEEE'
    input_board = sys.argv[1]
    tLimit = int(sys.argv[2])

    mainboard = board(input_board)

    playerTurn = input_board[0]

    player, opponent = getPly(playerTurn)
    White_Player = 'O'
    Black_Player = 'X'

    pmoves = getMoves(mainboard, opponent)

    if isFinished(mainboard, opponent):
        print("pass")

    elif pmoves == []:
        print("pass")

    else:
        roundStartTime = time()
        r, c = play(mainboard, opponent)
        print('(%d,%d)' % (c + 1, r + 1))


if __name__ == '__main__':
    main()
