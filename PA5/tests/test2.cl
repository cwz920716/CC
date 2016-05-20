class Main inherits IO {
  y:Int <- 5;
  main():Object {
    out_int(3 + test_1(4) + test_1(11) * (2*y-1) + (let x:Int <- 2 in
                                                    let z:Int <- 3 + x in
                                                    x+z))
  };

  test_1(x:Int):Int {{
      while  x < 10
      loop
        x <- x + 1
      pool;
      x;
  }};
};
