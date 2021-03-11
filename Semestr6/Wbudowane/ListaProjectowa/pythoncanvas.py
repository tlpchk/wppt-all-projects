from tkinter import *
import math
import numpy as np
from tkinter.colorchooser import askcolor


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

        self.pen_button = Button(self.root, text='angles', command=self.printAngles)
        self.pen_button.grid(row=0, column=4)

        self.c = Canvas(self.root, bg='white', width=600, height=600)
        self.c.grid(row=1, columnspan=5)

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
        # self.c.bind('<ButtonRelease-1>', self.reset)

    def save(self):
        # self.activate_button(self.pen_button)
        pass

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
        print("--------------")
        for l in self.lines:
            print(l)

    def dotproduct(self, v1, v2):
        print("Dot product of ", v1, " and ", v2, " is ", (v1.start.x * v2.start.x + v1.end.y * v2.end.y))
        return v1.cords[0] * v2.cords[0] + v1.cords[1] * v2.cords[1]

    def length(self, v):
        print("Length of ", v, " is ", math.sqrt(self.dotproduct(v, v)))
        return math.sqrt(self.dotproduct(v, v))

    def angle(self, v1, v2):
        return math.acos(self.dotproduct(v1, v2) / (self.length(v1) * self.length(v2)))

    def printAngles(self):
        print("--------ANGLES--------")
        for i in range(len(self.lines) - 1):
            radians = self.angle(self.lines[i], self.lines[i + 1])
            print(radians * 180 / math.pi)


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __str__(self):
        return "(" + str(self.x) + " , " + str(self.y) + ")"


class Line:
    def __init__(self, start, end):
        self.start = start
        self.end = end

    def __str__(self):
        return "(" + str(self.start) + " , " + str(self.end) + ")"

    def __len__(self):
        return math.sqrt(self.cords()[0] ** 2 + self.cords()[1] ** 2)

    def cords(self):
        return self.end.x - self.start.x, self.end.y - self.start.y


if __name__ == '__main__':
    Paint()
