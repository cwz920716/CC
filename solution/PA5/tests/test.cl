class Main inherits IO {
  y:Int <- 5;
  main():Object {
    "halt".abort()
  };

  test_1(x:Int):Int {{
      while  x < 5
      loop
        x <- x + 1
      pool;
      x;
  }};
};
