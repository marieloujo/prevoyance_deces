<?php

namespace App\Http\Requests;

use App\Http\Requests\User\RegisterRequest;
use Illuminate\Foundation\Http\FormRequest;

class ValidationRequest extends RegisterRequest
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
            'profession '  => ['require','string'],
            'employeur '  => ['require','string'],
        ];
    }
}
