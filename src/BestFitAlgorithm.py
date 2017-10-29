MAX_MEMORY = 640

free_memory_list = [[640, 1, 640]]


def BF(process_num: int, memory_need: int):
    start_address = 0
    for i in range(len(free_memory_list)):
        if (free_memory_list[i][0] >= memory_need):
            free_memory_list[i][0] -= memory_need
            start_address = free_memory_list[i][1]
            free_memory_list[i][1] += memory_need
            sort_free_part(i)
            break
    if(start_address == 0):
        print("process:", process_num, "alloc memory:", memory_need, "fail")
    else:
        print("process:", process_num, "alloc memory:", memory_need, "success")

    print_partition()

    return start_address


def free(process_num: int, memory_free: int, start_address: int):
    len_list = len(free_memory_list)
    for i in range(len(free_memory_list)):
        if (free_memory_list[i][0] > memory_free):
            free_memory_list.insert(i, [memory_free, start_address, start_address + memory_free - 1])
            break
    if (len_list == len(free_memory_list)):
        free_memory_list.insert(len(free_memory_list) - 1,
                                [memory_free, start_address, start_address + memory_free - 1])
    print("process:", process_num, "free memory:", memory_free, "success")
    print_partition()


def sort_free_part(start_index: int):
    for i in range(len(free_memory_list)):
        if (free_memory_list[i][0] > free_memory_list[start_index][0]):
            temp = free_memory_list.pop(start_index)
            if (i < start_index):
                free_memory_list.insert(i, temp)
            else:  # behind start_index this index will -1 after pop
                free_memory_list.insert(i - 1, temp)
            break


def print_partition():
    print("================================================================")
    for i in free_memory_list:
        print("memory partition:", i[1], "~", i[2], "size :", i[0], "free")
    print("================================================================\n")


if __name__ == '__main__':
    address = [0 for i in range(1, 10)]

    address[1] = BF(1, 130)
    address[2] = BF(2, 60)
    address[3] = BF(3, 100)
    free(2, 60, address[2])
    address[4] = BF(4, 200)
    free(3, 100, address[3])
    free(1, 130, address[1])
    address[5] = BF(5, 140)
    address[6] = BF(6, 60)
    address[7] = BF(7, 50)
    free(6, 60, address[6])
