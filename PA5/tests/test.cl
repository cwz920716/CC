class Main inherits IO {
  y:Int <- 5;
  main():Object {
    test_1(1).abort()
  };

  test_1(x:Int):Int {{
      while  x < 5
      loop
        x <- x + 1
      pool;
      x;
  }};
};
