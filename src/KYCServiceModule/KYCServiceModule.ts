import { NativeModules, Platform } from 'react-native';
import IKYCServiceModulePublic from './IKYCServiceModulePublic';
import IKYCServiceModulePrivate from './IKYCServiceModulePrivate';

let exportedModule: IKYCServiceModulePrivate | undefined = NativeModules.Au10tixRNModule;

class KYCServiceModule implements IKYCServiceModulePublic {
  private readonly setSDKToken = (token: string) => {
    exportedModule?.setJWT(token);
  }

  readonly prepare = async (token: string): Promise<boolean> => {
    this.setSDKToken(token);
    if (Platform.OS === 'ios') {
      return exportedModule?.prepare() ?? false;
    } else {
      return true;
    }
  }

  readonly startSDCSession = async (): Promise<string | undefined> => {
    if (exportedModule) {
      return await exportedModule.startSDCSession();
    }

    return undefined;
  }

  readonly startLivenessSession = async (): Promise<string | undefined> => {
    if (exportedModule) {
      return await exportedModule.startLivenessSession();
    }

    return undefined;
  }

  readonly cleanSession = () => {
    if (exportedModule) {
      exportedModule.cleanSession();
    }
  }

  readonly getSDKVersion = () => {
    if (exportedModule) {
      exportedModule.getSDKVersion();
    }
  }
}

export default new KYCServiceModule();

