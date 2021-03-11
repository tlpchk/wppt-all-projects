from tkinter import *
from tkinter.colorchooser import askcolor
from tkinter import filedialog as fd
import math 
import copy


class Paint(object):

    DEFAULT_PEN_SIZE = 5.0
    DEFAULT_COLOR = 'black'

    lines = []

    def __init__(self):
        self.root = Tk()

        self.pen_button = Button(self.root, text='save', command=self.save)
        self.pen_button.grid(row=0, column=0)

        self.erase_button = Button(self.root, text='erase', command=self.use_erase)
        self.erase_button.grid(row=0, column=1)

        self.choose_size_button = Scale(self.root, from_=1, to=10, orient=HORIZONTAL)
        self.choose_size_button.grid(row=0, column=2)

        self.pen_button = Button(self.root, text='print', command=self.printLines)
        self.pen_button.grid(row=0, column=3)

        self.c = Canvas(self.root, bg='white', width=600, height=600)
        self.c.grid(row=1, columnspan=4)

        self.setup()
        self.root.mainloop()

    def setup(self):
        self.old_x = None
        self.old_y = None
        self.line_width = self.choose_size_button.get()
        self.color = self.DEFAULT_COLOR
        self.eraser_on = False
        self.active_button = self.pen_button
        self.c.bind('<Button-1>', self.paint)

    def save(self):
        filename = fd.asksaveasfilename(filetypes=[("Plik tekstowy","*.txt")], defaultextension = "*.txt") # wywołanie okna dialogowego save file
        
        if filename:
            with open(filename, "w", -1, "utf-8") as file:
                commands = self.pointsToCommands()
                for cmd in commands:
                    file.write("%s\n" % cmd)
        


    def use_erase(self):
        self.c.delete("all")
        self.reset()
        self.lines = []

    def activate_button(self, some_button, eraser_mode=False):
        self.active_button.config(relief=RAISED)
        some_button.config(relief=SUNKEN)
        self.active_button = some_button
        self.eraser_on = eraser_mode

    def paint(self, event):
        self.line_width = self.choose_size_button.get()
        paint_color = self.color
        if self.old_x and self.old_y:
            self.c.create_line(self.old_x, self.old_y, event.x, event.y,
                               width=self.line_width, fill=paint_color,
                               capstyle=ROUND, smooth=TRUE, splinesteps=36)
            self.lines.append(Line(Point(self.old_x, self.old_y), Point(event.x, event.y)))

        self.old_x = event.x
        self.old_y = event.y

    def reset(self):
        self.old_x, self.old_y = None, None

    def printLines(self):
        cmds = self.pointsToCommands()
        for cmd in cmds:
            print(cmd)

    def pointsToCommands(self):
        commands = []
        if not self.lines:
            return commands
        lines = copy.deepcopy(self.lines)
        first_line = lines.pop(0)
        commands.append("F "+str(self.vectorLength(first_line.start, first_line.end)))
        prev_vector = [first_line.end.x - first_line.start.x, first_line.end.y - first_line.start.y]
        for line in lines:
            current_vector = [line.end.x - line.start.x, line.end.y - line.start.y]
            angle_rad = self.getAngle(prev_vector, current_vector)
            angle_degrees = math.degrees(angle_rad)

            if angle_rad < 0:
                if abs(angle_degrees) < 180:
                    commands.append("L "+ str(abs(angle_degrees)))
                else:
                    commands.append("R "+ str(360 - abs(angle_degrees)))
            elif angle_rad > 0:
                if abs(angle_degrees) < 180:
                    commands.append("R "+ str(abs(angle_degrees)))
                else:
                    commands.append("L "+ str(360 - abs(angle_degrees)))
                pass
            else:
                pass
            commands.append("F " +str(self.vectorLength(line.start, line.end)))
            prev_vector = copy.deepcopy(current_vector)
        return commands
        
        

    def vectorLength(self, a, b):
        return math.sqrt((a.x - b.x)**2 + (a.y-b.y)**2)

    def getAngle(self, a, b):
        angle_rad = math.atan2(b[1],b[0]) - math.atan2(a[1],a[0])
        angle_degrees = math.degrees(angle_rad)
        return angle_rad

    def b2Cross(self, a, b):
        return a.x * b.y - a.y * b.x
        

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    def __str__(self):
        return "("+ str(self.x) + " , " + str(self.y) + ")"

class Line:
    def __init__(self, start, end):
        self.start = start
        self.end = end
    def __str__(self):
        return "(" + str(self.start) + " , " + str(self.end) + ")"

if __name__ == '__main__':
    Paint()