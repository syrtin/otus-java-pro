## Results of hw04-gc

### Original Code

| Heap, Mb |                 Result |
|:--------:|-----------------------:|
|   256    |       OutOfMemoryError |
|   384    | spend msec:9851, sec:9 |
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
|   512    | spend msec:1301, sec:1 |
|   1024   | spend msec:1329, sec:1 |
|   2048   | spend msec:1340, sec:1 |
|   3072   | spend msec:1277, sec:1 |
|   4096   | spend msec:1233, sec:1 |
|   5120   | spend msec:1293, sec:1 |
|   6144   | spend msec:1312, sec:1 |
|   8196   | spend msec:1292, sec:1 |

### Conclusions for original / optimized
#### 1. Best performance at 4096 Mb / 4096 Mb
#### 2. Best efficiency at 1024 Mb / 512 Mb
#### 3. Integer -> int optimization speed up ~7 times