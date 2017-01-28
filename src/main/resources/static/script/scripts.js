var app = angular.module('myApp', []);

app.controller('ContaController', ['$scope', '$timeout', 'contaFactory', function($scope, $timeout, contaFactory) {
	
	$scope.entity = {agenciaOrigem:'', numeroOrigem:'', agenciaDestino:'', numeroDestino:'', valor:''};
	$scope.error = {message:'', status:false}

	$scope.campos = [{label:'Agencia Origem', name:'agenciaOrigem', value:false},
	                 {label:'Conta Origem', name:'numeroOrigem', value:false},
	                 {label:'Agencia Destino', name:'agenciaDestino', value:false},
	                 {label:'Conta Destino', name:'numeroDestino', value:false},
	                 {label:'Valor', name:'valor', value:false}];
	
	$scope.valid = false;
	$scope.success = false;
	
	$scope.transferir = function() {
		$scope.error = {message:'', status:false}
		var rt = $scope.validar();
		if (!rt) {
			$scope.valid = false;
			contaFactory.transferir($scope.entity)
			.then(function(s) {
				console.log('Transferido com sucesso!!!');
				$scope.limpar();
				$scope.success = true;
				$timeout(start, 6000);
			},
			function(e) {
				console.error("error", e);
				if ('message' in e.data) {
					$scope.error.message = e.data.message;
					$scope.error.status = true;
				}
			})
		} else {
			$scope.valid = true;
		}
	}
	
	$scope.limpar = function() {
		$scope.entity.agenciaOrigem = '';
		$scope.entity.numeroOrigem = '';
		$scope.entity.agenciaDestino = '';
		$scope.entity.numeroDestino = '';
		$scope.entity.valor = '';
	}
	
	$scope.validar = function() {
		$scope.campos = [{label:'Agencia Origem', name:'agenciaOrigem', value:false},
		                 {label:'Conta Origem', name:'numeroOrigem', value:false},
		                 {label:'Agencia Destino', name:'agenciaDestino', value:false},
		                 {label:'Conta Destino', name:'numeroDestino', value:false},
		                 {label:'Valor', name:'valor', value:false}];
		var ver = false;
		for (var i=0, len=$scope.campos.length; i<len; i++) {
			if ($scope.entity[$scope.campos[i].name] == '' || $scope.entity[$scope.campos[i].name] == undefined) {
				$scope.campos[i].value = true;
				ver = true;
			}
		}
		return ver;
	}
	
	var start = function() {
		$scope.success = false;
	}

}]);

app.factory('contaFactory', function($http) {

	var contaFactory = {};

	contaFactory.transferir = function(conta) {
		var promisse = $http.post('/conta', conta)
		.then(function(response) {
			return response.data;
		});
		return promisse;
	}

	return contaFactory;
});

app.directive('onlyNum', function() {
    return function(scope, element, attrs) {

        var keyCode = [8,9,37,39,48,49,50,51,52,53,54,55,56,57,96,97,98,99,100,101,102,103,104,105,110];
         element.bind("keydown", function(event) {
           console.log($.inArray(event.which,keyCode));
           if($.inArray(event.which,keyCode) == -1) {
               scope.$apply(function(){
                   scope.$eval(attrs.onlyNum);
                   event.preventDefault();
               });
               event.preventDefault();
           }

       });
    };
 });