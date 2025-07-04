/* tslint:disable */
/* eslint-disable */
/**
 * Croqueteria - API Documentation
 * Endpoints for Croqueteria API, a service that allows to describe, rate and discuss the most delicious croquettes around the globe.
 *
 * The version of the OpenAPI document: v1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface UserUpdateRequest
 */
export interface UserUpdateRequest {
    /**
     * New password for the user, optional
     * @type {string}
     * @memberof UserUpdateRequest
     */
    password?: string;
    /**
     * Updated user role, optional
     * @type {string}
     * @memberof UserUpdateRequest
     */
    role?: UserUpdateRequestRoleEnum;
}


/**
 * @export
 */
export const UserUpdateRequestRoleEnum = {
    Anon: 'ANON',
    User: 'USER',
    Manager: 'MANAGER'
} as const;
export type UserUpdateRequestRoleEnum = typeof UserUpdateRequestRoleEnum[keyof typeof UserUpdateRequestRoleEnum];


/**
 * Check if a given object implements the UserUpdateRequest interface.
 */
export function instanceOfUserUpdateRequest(value: object): value is UserUpdateRequest {
    return true;
}

export function UserUpdateRequestFromJSON(json: any): UserUpdateRequest {
    return UserUpdateRequestFromJSONTyped(json, false);
}

export function UserUpdateRequestFromJSONTyped(json: any, ignoreDiscriminator: boolean): UserUpdateRequest {
    if (json == null) {
        return json;
    }
    return {
        
        'password': json['password'] == null ? undefined : json['password'],
        'role': json['role'] == null ? undefined : json['role'],
    };
}

export function UserUpdateRequestToJSON(json: any): UserUpdateRequest {
    return UserUpdateRequestToJSONTyped(json, false);
}

export function UserUpdateRequestToJSONTyped(value?: UserUpdateRequest | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'password': value['password'],
        'role': value['role'],
    };
}

