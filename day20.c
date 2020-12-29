/*
 * Compile this with -O3 for reasonable performance
 */
#include <time.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

/*
 * We don't really need all the divisors, just the sum.
 */
int even_divisors(int n, int* out) {
    int num = 0;
    for (int i=1; i <= sqrt(n); ++i) {
        if (n%i == 0) {
            out[num++] = i;
            if (n/i != i)
                out[num++] = n/i;
        }
    }
    return num;
}

int num_presents_at_house(int house_number) {
    int even_divisor_list[10000];
    int num_even_divisors = even_divisors(house_number, even_divisor_list);

    int sum = 0;
    for (int i=0; i < num_even_divisors; ++i) {
        sum += even_divisor_list[i];
    }
    return sum*10;
}

int num_presents_at_house_2(int house_number) {
    int even_divisor_list[10000];
    int num_even_divisors = even_divisors(house_number, even_divisor_list);

    int sum = 0;
    for (int i=0; i < num_even_divisors; ++i) {
        // has this elf already delivered to 50 houses?
        if (house_number / even_divisor_list[i] <= 50)
            sum += even_divisor_list[i];
    }
    return sum*11;
}

/**
 * @fn timespec_diff(struct timespec *, struct timespec *, struct timespec *)
 * @brief Compute the diff of two timespecs, that is a - b = result.
 * @param a the minuend
 * @param b the subtrahend
 * @param result a - b
 */
static inline void timespec_diff(struct timespec *a, struct timespec *b,
    struct timespec *result) {
    result->tv_sec  = a->tv_sec  - b->tv_sec;
    result->tv_nsec = a->tv_nsec - b->tv_nsec;
    if (result->tv_nsec < 0) {
        --result->tv_sec;
        result->tv_nsec += 1000000000L;
    }
}

int main(int argc, char** argv) {
    struct timespec start_time;
    clock_gettime(CLOCK_MONOTONIC, &start_time);

    size_t n = 33100000;
    int house_number;
    for (house_number = 1; house_number < n; ++house_number) {
        /* if (house_number < 10)
            printf("House %d got %d presents\n", house_number, num_presents_at_house(house_number));
        */
        if (num_presents_at_house(house_number) >= n)
            break;
    }
    printf("Day 20 part 1: %d\n", house_number);

    for (house_number = 1; house_number < n; ++house_number) {
        if (num_presents_at_house_2(house_number) >= n)
            break;
    }
    printf("Day 20 part 2: %d\n", house_number);

    struct timespec stop_time;
    clock_gettime(CLOCK_MONOTONIC, &stop_time);

    struct timespec time_to_calculate;
    timespec_diff(&stop_time, &start_time, &time_to_calculate);
    printf("%ld.%09ld\n", time_to_calculate.tv_sec, time_to_calculate.tv_nsec);

    return 1;
}
