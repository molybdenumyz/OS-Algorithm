# -*- coding: utf-8 -*-
MAX_MEMORY = 640
memory = [0 for i in range(MAX_MEMORY + 1)]


def FF(process_num: int, memory_need: int):
    find_memory_now = 0
    start_memory_address = 0
    return_address = 0
    for index, value in enumerate(memory):
        if (index == 0):
            continue
        elif (find_memory_now == memory_need):
            break
        elif (value == 0 and start_memory_address == 0):
            start_memory_address = index
            find_memory_now += 1
        elif (value == 0 and start_memory_address != 0):
            find_memory_now += 1
        elif (find_memory_now < memory_need and value != 0):
            find_memory_now = 0
            start_memory_address = 0

    if (find_memory_now < memory_need):
        print("process:", process_num, "alloc memory:", memory_need, "fail")

    elif (find_memory_now == memory_need):
        alloc(process_num, memory_need, start_memory_address)

        print_partition(memory)
        return_address = start_memory_address

    return return_address


def free(process_num: int, memory_free: int, start_memory_address: int):
    for index in range(start_memory_address, start_memory_address + memory_free):
        memory[index] = 0
    print("process:", process_num, "free memory:", memory_free, "success")
    print_partition(memory)


def alloc(process_num: int, memory_need: int, start_memory_address: int):
    for index in range(start_memory_address, start_memory_address + memory_need):
        memory[index] = process_num
    print("process:", process_num, "alloc memory:", memory_need, "success")


def print_partition(memory: []):
    print("====================================================")
    start = 0

    for index, value in enumerate(memory):
        if (index == 1 or index == 0):
            continue;
        elif (value == memory[index - 1] and start == 0):
            start = index - 1
        elif (value == memory[index - 1] and start != 0):
            continue
        elif (value != memory[index - 1]):
            if (memory[index - 1] != 0):
                print("memory partition:", start, "~", index - 1, "occupied by process: ", memory[index - 1])
            else:
                print("memory partition:", start, "~", index - 1, "free")
            start = index
    print("memory partition:", start, "~", MAX_MEMORY, "free")

    print("====================================================\n")


if __name__ == '__main__':
    address = [0 for i in range(1, 10)]

    address[1] = FF(1, 130)
    address[2] = FF(2, 60)
    address[3] = FF(3, 100)
    free(2, 60, address[2])
    address[4] = FF(4, 200)
    free(3, 100, address[3])
    free(1, 130, address[1])
    address[5] = FF(5, 140)
    address[6] = FF(6, 60)
    address[7] = FF(7, 50)
    free(6, 60, address[6])
