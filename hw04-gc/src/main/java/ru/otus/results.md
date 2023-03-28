## Results of hw04-gc

### Original Code

| Heap, Mb |                 Result |
|:--------:|-----------------------:|
|   256    |       OutOfMemoryError |
|   512    | spend msec:9668, sec:9 |
|   1024   | spend msec:8960, sec:8 |
|   2048   | spend msec:8530, sec:8 |
|   3072   | spend msec:8497, sec:8 |
|   4096   | spend msec:8430, sec:8 |
|   5120   | spend msec:8564, sec:8 |
|   6144   | spend msec:8702, sec:8 |
|   8196   | spend msec:9014, sec:9 |

### Optimized Code (Integer -> int)

| Heap, Mb |                 Result |
|:--------:|-----------------------:|
|   256    | spend msec:1846, sec:1 |
|   384    | spend msec:1355, sec:1 |
|   512    | spend msec:1297, sec:1 |
|   1024   | spend msec:1329, sec:1 |
|   2048   | spend msec:1340, sec:1 |

### Optimal heap size:
#### for original code - 4096 Mb
#### for optimized code - 512 Mb