<?php

namespace App\Http\Requests;

use App\Http\Requests\ValidationRequest;
use App\Http\Requests\BeneficeRequest;
use App\Http\Requests\UtilisateurRequest;
use Illuminate\Foundation\Http\FormRequest;

class ContratRequest extends FormRequest
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
      $formRequests = [
        UtilisateurRequest::class,
        BeneficeRequest::class,
      ];
  
      $rules = [];
  
      foreach ($formRequests as $source) {
        $rules = array_merge(
          $rules,
          (new $source)->rules()
        );
      }
  
      return $rules;

    }

    
}
