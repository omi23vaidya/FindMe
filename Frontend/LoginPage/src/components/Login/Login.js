//import libraries
//import React, {Component} from 'react';
var React = require('react');
var Component = require('react');
import {View, Text, StyleSheet} from 'react-native';

//creat component
class Login extends Component {
    render() {
        return (
            <View style = {styles.Component}>
                {/* <Text>Login</Text>
                 */}
                    <View style={styles.loginContainer}>
                        <Image resizeMode="contain" style={styles.logo} source={require('../../components/images/find-me-logo.png')} />
                    </View>

                    <View style={styles.formContainer}>
                        <LoginForm />
                    </View>
            </View>
        );
    }
}

//define your styles
const styles = StyleSheet.create({
    container: {
        flex:1,
        backgroundColor: '#2c3e50',
    },
    loginContainer:{
        alignItems: 'center',
        flexGrow: 1,
        justifyContent: 'center'
    },
    logo: {
        position: 'absolute',
        width: 300,
        height: 100
    }
});