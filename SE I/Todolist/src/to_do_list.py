"""write your code in following methods"""
file_path = './tasks.txt'

def to_do():
    instr = list(input().split('"'))
    m = instruction(instr[0])
    while m != 7:
        if m == 1:
            addToDO(instr[1::2])
        elif m == 2:
            delete(instr[1::2])
        elif m == 3:
            changeStatus(instr[1::2])
        elif m == 4:
            findStatus("todo")
        elif m == 5:
            findStatus("completed")
        elif m == 6:
            showAll()
        instr = list(input().split('"'))
        m = instruction(instr[0])
    return


def instruction(ins):
    # 读入命令，返回数字：
    # 1——to do -a
    # 2——to do -d
    # 3——to do -c
    # 4——to do -f
    # 5——to do -all
    # 6——to do -quit
    if ins == 'todo -a ':
        return 1
    elif ins == 'todo -d ':
        return 2
    elif ins == 'todo -c ':
        return 3
    elif ins == 'todo -f todo':
        return 4
    elif ins == 'todo -f completed':
        return 5
    elif ins == 'todo -all':
        return 6
    elif ins == 'todo -quit':
        return 7


def addToDO(toDoList):
    with open(file_path, 'a') as f:
        for str in toDoList:
            f.write("todo:" + str + '\n')


def delete(toDelete):
    with open(file_path, 'r') as r:
        lines = r.readlines()
    with open(file_path, 'w') as w:
        for l in lines:
            if l.split(':')[1].strip() in toDelete:
                continue
            else:
                w.write(l)

def changeStatus(toChange):
    with open(file_path, 'r') as r:
        lines = r.readlines()
    with open(file_path, 'w') as w:
        for l in lines:
            if l.split(':')[1].strip() in toChange:
                w.write("completed:" + l.split(':')[1])
            else:
                w.write(l)

def findStatus(status):
        with open(file_path, 'r') as r:
            lines = r.readlines()
            for l in lines:
                if status in l:
                    print(l.strip())

def showAll():
    with open(file_path, 'r') as r:
        lines = r.readlines()
        for l in lines:
            print(l.strip())

if __name__=="__main__":
    to_do()