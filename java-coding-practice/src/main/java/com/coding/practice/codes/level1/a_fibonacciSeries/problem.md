The **Fibonacci series** is a sequence of numbers where each number is the sum of the two preceding ones.

---

### 🔢 Fibonacci Sequence:

```
0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
```

### 📘 Formula:

```
F(n) = F(n - 1) + F(n - 2)
```

With base values:

```
F(0) = 0  
F(1) = 1
```

---

### ✅ Sample Java Code:

```java
public class Fibonacci {
    public static void main(String[] args) {
        int n = 10;
        int a = 0, b = 1;
        System.out.print("Fibonacci series: ");
        for (int i = 1; i <= n; i++) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;
        }
    }
}
```

Let me know if you want it with recursion or `Stream`.
