class Main inherits IO {
  y:Int <- 5;
  main():Object {
    let x:Int <- 1 in 
      out_int(test_1(x))
  };

  test_1(x:Int):Int {{
      while  x < 5
      loop
        x <- x + 1
      pool;
      x;
  }};
};
