<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<!DOCTYPE html>
<html  xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="windows-1255">
<title>Auto complete exercise</title>

<style>
* {
  box-sizing: border-box;
}

body {
  font: 16px Arial;  
}

/*the container must be positioned relative:*/
.autocomplete {
  position: relative;
  display: inline-block;
}

input {
  border: 1px solid transparent;
  background-color: #f1f1f1;
  padding: 10px;
  font-size: 16px;
}

input[type=text] {
  background-color: #f1f1f1;
  width: 100%;
}

input[type=submit] {
  background-color: DodgerBlue;
  color: #fff;
  cursor: pointer;
  pointer-events:none;
}

.autocomplete-items {
  position: absolute;
  border: 1px solid #d4d4d4;
  border-bottom: none;
  border-top: none;
  z-index: 99;
  /*position the autocomplete items to be the same width as the container:*/
  top: 100%;
  left: 0;
  right: 0;
}

.autocomplete-items div {
  padding: 10px;
  cursor: pointer;
  background-color: #fff; 
  border-bottom: 1px solid #d4d4d4; 
}

/*when hovering an item:*/
.autocomplete-items div:hover {
  background-color: #e9e9e9; 
}

/*when navigating through the items using the arrow keys:*/
.autocomplete-active {
  background-color: DodgerBlue !important; 
  color: #ffffff; 
}
</style>
</head>

<body>

	<h2>Auto complete exercise</h2>
	<form autocomplete="off">
	  <div class="autocomplete" style="width:300px;">
	    <input id="my_input" type="text" id="prefix_input" oninput="autoCompHttpRequest(event)" name="myCountry" placeholder="Name">
	  </div>
  	  <input type="submit">
	</form>
	<script>

	let _inp = document.getElementById("my_input");
	function autoCompHttpRequest(e){
		
		let prefix = document.getElementById('my_input').value;
			fetch('/api?prefix='+prefix)
			.then(response => {
				if(response.ok)
				return response.json()
				throw new Error('Invalid input');
			})
			.then(data => showSuggestions(e,data))
			.catch(e=>console.log(e))
	}
	
		  
		  var currentFocus;
		  /*execute a function when someone writes in the text field:*/
		  function showSuggestions(e,data) {
			  if(data == null)
				  return;
		      var a, b, i, val = e.target.value;
		      /*close any already open lists of autocompleted values*/
		      closeAllLists();
		      if (!val) { return false;}
		      currentFocus = -1;
		      /*create a DIV element that will contain the items (values):*/
		      a = document.createElement("DIV");
		      a.setAttribute("id", this.id + "autocomplete-list");
		      a.setAttribute("class", "autocomplete-items");
		      /*append the DIV element as a child of the autocomplete container:*/
		      e.target.parentNode.appendChild(a);
		      /*for each item in the array...*/
		      for (i = 0; i < data.length; i++) {
		          /*create a DIV element for each matching element:*/
		          b = document.createElement("DIV");
		          /*make the matching letters bold:*/
		          b.innerHTML = "<strong>" + data[i].substr(0, val.length) + "</strong>";
		          b.innerHTML += data[i].substr(val.length);
		          /*insert a _input field that will hold the current array item's value:*/
		          b.innerHTML += "<input type='hidden' value='" + data[i] + "'>";
		          /*execute a function when someone clicks on the item value (DIV element):*/
		              b.addEventListener("click", function(e) {
		              /*insert the value for the autocomplete text field:*/
		              _inp.value = e.target.getElementsByTagName("input")[0].value;
		              /*close the list of autocompleted values,
		              (or any other open lists of autocompleted values:*/
		              closeAllLists();
		          });
		          a.appendChild(b);
		      }
		  };
		  /*execute a function presses a key on the keyboard:*/
		  _inp.addEventListener("keydown", function(e) {
		      var x = document.getElementById(this.id + "autocomplete-list");
		      if (x) x = x.getElementsByTagName("div");
		      if (e.keyCode == 40) {
		        /*If the arrow DOWN key is pressed,
		        increase the currentFocus variable:*/
		        currentFocus++;
		        /*and and make the current item more visible:*/
		        addActive(x);
		      } else if (e.keyCode == 38) { //up
		        /*If the arrow UP key is pressed,
		        decrease the currentFocus variable:*/
		        currentFocus--;
		        /*and and make the current item more visible:*/
		        addActive(x);
		      } else if (e.keyCode == 13) {
		        /*If the ENTER key is pressed, prevent the form from being submitted,*/
		        e.preventDefault();
		        if (currentFocus > -1) {
		          /*and simulate a click on the "active" item:*/
		          if (x) x[currentFocus].click();
		        }
		      }
		  });
		  function addActive(x) {
		    /*a function to classify an item as "active":*/
		    if (!x) return false;
		    /*start by removing the "active" class on all items:*/
		    removeActive(x);
		    if (currentFocus >= x.length) currentFocus = 0;
		    if (currentFocus < 0) currentFocus = (x.length - 1);
		    /*add class "autocomplete-active":*/
		    x[currentFocus].classList.add("autocomplete-active");
		  }
		  function removeActive(x) {
		    /*a function to remove the "active" class from all autocomplete items:*/
		    for (var i = 0; i < x.length; i++) {
		      x[i].classList.remove("autocomplete-active");
		    }
		  }
		  function closeAllLists(elmnt) {
		    /*close all autocomplete lists in the document,
		    except the one passed as an argument:*/
		    var x = document.getElementsByClassName("autocomplete-items");
		    for (var i = 0; i < x.length; i++) {
		      if (elmnt != x[i] && elmnt != _inp) {
		      x[i].parentNode.removeChild(x[i]);
		    }
		  }
		}
		/*execute a function when someone clicks in the document:*/
		document.addEventListener("click", function (e) {
		    closeAllLists(e.target);
		});
	</script>
</body>
</html>