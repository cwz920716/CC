class Main inherits IO {
  y:Int <- 5;
  main():Object {
    let thing : Int <- y in
      case thing of
	x : IO => out_string( "IO\n" );
	m : Main => out_string( "Main\n" );
      esac
  };

  test_1(x:Int):Int {{
      while  x < 5
      loop
        x <- x + 1
      pool;
      x;
  }};
};
