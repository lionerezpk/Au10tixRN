import React, { useState } from 'react';
import { Alert, NativeSyntheticEvent, PermissionsAndroid, PermissionStatus, StyleSheet, Text, TextInput, TextInputChangeEventData, TouchableOpacity, View } from 'react-native';
import KYCServiceModule from './KYCServiceModule/KYCServiceModule';

type ButtonType = 'set_token' | 'sdc';

const App = () => {

  const [tokenValue, setTokenValue] = useState<string | undefined>();
  const [isSDKReady, setIsSDKReady] = useState(false);

  const onButtonPressed = (type: ButtonType) => {
    console.log('token value ::: ', tokenValue);
    if (type === 'set_token' && tokenValue) {
      KYCServiceModule.prepare(tokenValue).then((ready: boolean) => {
        if (ready) {
          setIsSDKReady(true);
        } else {
          Alert.alert('SDK Prepartion Failed');
        }
      });
      return;
    }
    if (type === 'sdc') {
      PermissionsAndroid.request('android.permission.CAMERA').then((permissionsResult: PermissionStatus) => {
        if (permissionsResult === 'granted' && isSDKReady) {
          KYCServiceModule.startSDCSession().then((sdcResult?: string) => {
            console.log('sdc result ??? ', sdcResult);
            Alert.alert('SDC RESULT :::: ', sdcResult);
          })
        }
      });
    }
  }

  return (
    <View style={styles.container} >
      {
        !isSDKReady && (
          <View style={{ marginTop: 24, width: '100%', alignItems: 'center' }}>
            <TextInput
              style={{ width: '95%', height: 54, fontSize: 24, }}
              placeholderTextColor='#000'
              placeholder='Set Token For Use'
              onChange={(event: NativeSyntheticEvent<TextInputChangeEventData>) => setTokenValue(event.nativeEvent.text)}
              autoFocus
            />
            <TouchableOpacity
              style={[styles.button, { marginTop: 24, }]}
              onPress={() => onButtonPressed('set_token')}
            >
              <Text style={styles.buttonText}>Apply Token</Text>
            </TouchableOpacity>
          </View>
        )
      }

      {
        isSDKReady && (
          <View style={styles.innerContainer}>
            <View style={styles.buttonsContainer}>
              <TouchableOpacity
                style={styles.button}
                onPress={() => onButtonPressed('sdc')}
              >
                <Text style={styles.buttonText}>SDC Session</Text>
              </TouchableOpacity>
            </View>
          </View>
        )
      }

    </View>
  )
}

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    alignItems: 'center',
  },
  innerContainer: {
    flex: 1,
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center'
  },
  buttonsContainer: {
    flexDirection: 'column',
  },
  button: {
    width: 144,
    height: 54,
    backgroundColor: '#3333',
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
  },
  buttonText: {
    fontSize: 18,
    fontWeight: 'bold',
  }
})
