node {
    stage('Checkout') {
        git url: 'https://github.com/tjmuscles/metroDay3.git'
    }
    
    stage('Gradle build') {
        sh 'gradle build -x test'
    }
    
    stage('User Acceptance Test') {
    
    	def response= input message: 'Is this build good to go?',
    	parameters: [choice(choices: 'Yes\nNo',
    	description: '', name: 'Pass')]
    	
    	if(response=="Yes") {
    		stage('Deploy') {
    			sh 'gradle test'
			}
		}
	}
}