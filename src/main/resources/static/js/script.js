console.log("This is a script file")

const toggleSidebar = () => {
	
	if($(".sidebar").is(":visible")){
		//true
		//band karnahe
		
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","2%");
	}else{
		//false
		//phir show karnahe
		
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");	
	}
} ;