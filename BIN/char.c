		for(int i=0;i<args.length;i++){	
			if(args[i].endsWith(".c"))
				inputFile=args[i];
			if(args[i].equals("-e")){
				useStop=true;
				if((i!=(args.length-1))&&args[i+1].endsWith(".txt"))
					stopFile=args[i+1];
			}
