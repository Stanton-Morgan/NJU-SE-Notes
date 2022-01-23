import unittest
import sys
import os
from src import to_do_list


class TestProblem(unittest.TestCase):
    def setUp(self):
        self.stream_out = MyStream()
        self.stream_in = MyStream()
        self.out_stream = sys.stdout
        self.in_stream = sys.stdin
        sys.stdout = self.stream_out
        sys.stdin = self.stream_in
        to_do_list.file_path = "./tasks.txt"
        if os.path.exists("./tasks.txt"):
            os.remove("./tasks.txt")
        pass

    def test_listA(self):
        self.stream_in.write('''todo -a "complete homework" "do handwriting"''')
        self.stream_in.write('todo -all')
        self.stream_in.write("todo -quit")

        to_do_list.to_do()
        result = self.stream_out.buff

        data = ["todo:complete homework", "todo:do handwriting"]

        j = 0
        for i in range(len(result)):
            if result[i] != '\n':
                self.assertEqual(result[i], data[j], 'Show list error after adding')
                j += 1

    def test_listB(self):
        self.stream_in.write('''todo -a "complete homework" "do handwriting"''')
        self.stream_in.write('''todo -a "go shopping" "read books"''')
        self.stream_in.write('todo -all')
        self.stream_in.write("todo -quit")

        to_do_list.to_do()
        result = self.stream_out.buff

        data = ["todo:complete homework", "todo:do handwriting", "todo:go shopping", "todo:read books"]

        j = 0
        for i in range(len(result)):
            if result[i] != '\n':
                self.assertEqual(result[i], data[j], 'Show list error after adding')
                j += 1

    def test_listC(self):
        self.stream_in.write('''todo -a "complete homework" "do handwriting"''')
        self.stream_in.write('''todo -a "go shopping" "visit grandparents" "participate the interview"''')
        self.stream_in.write('''todo -d "go shopping" "do handwriting"''')
        self.stream_in.write('todo -all')
        self.stream_in.write("todo -quit")

        to_do_list.to_do()
        result = self.stream_out.buff

        data = ["todo:complete homework", "todo:visit grandparents", "todo:participate the interview"]

        j = 0
        for i in range(len(result)):
            if result[i] != '\n':
                self.assertEqual(result[i], data[j], 'Show list error after deleting')
                j += 1

    def test_listD(self):
        self.stream_in.write('''todo -a "have lunch" "wash dishes" "do eye exercises" "read books" "play games"''')
        self.stream_in.write('''todo -d "wash dishes"''')
        self.stream_in.write('''todo -c "play games" "do eye exercises"''')
        self.stream_in.write('todo -all')
        self.stream_in.write("todo -quit")

        to_do_list.to_do()
        result = self.stream_out.buff

        data = ["todo:have lunch", "completed:do eye exercises", "todo:read books", "completed:play games"]

        j = 0
        for i in range(len(result)):
            if result[i] != '\n':
                self.assertEqual(result[i], data[j], 'Show list error after deleting')
                j += 1

    def test_listE(self):
        self.stream_in.write('''todo -a "visit grandparents" "do handwriting" "do eye exercises" "play games"''')
        self.stream_in.write('''todo -c "visit grandparents" "do eye exercises"''')
        self.stream_in.write('todo -f completed')
        self.stream_in.write("todo -quit")

        to_do_list.to_do()
        result = self.stream_out.buff

        data = ["completed:visit grandparents", "completed:do eye exercises"]

        j = 0
        for i in range(len(result)):
            if result[i] != '\n':
                self.assertEqual(result[i], data[j], 'Show list error after deleting')
                j += 1

    def tearDown(self):
        sys.stdout = self.out_stream
        sys.stdin = self.in_stream
        pass


class MyStream:
    def __init__(self):
        self.buff = []
        self.write_count = 0

    def write(self, output_stream):
        self.buff.append(output_stream)

    def readline(self):
        if len(self.buff) > 0:
            cur = self.buff[0]
            del self.buff[0]
            return cur
