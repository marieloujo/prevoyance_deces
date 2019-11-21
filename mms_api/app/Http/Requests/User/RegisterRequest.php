<?php

namespace App\Http\Requests\User;

use Illuminate\Foundation\Http\FormRequest;

class RegisterRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'nom' => ['required', 'string', 'max:255'],
            'prenom' => ['required', 'string', 'max:255'],
            'telephone' => ['required', 'integer', 'max:255'],
            'adresse' => ['sometimes', 'string', 'max:255'],
            'sexe' => 'required',
            'date_naissance' => ['required','date','before_or_equal:74','after_or_equal:18'],
            'situation_matrimoniale' => 'sometimes',
            'email' => ['sometimes', 'string', 'max:255'],
            'login' => ['required','string', 'unique:users'],
            'password' => ['required', 'string', 'min:8', 'confirmed'],
            
        ];
    }
}
