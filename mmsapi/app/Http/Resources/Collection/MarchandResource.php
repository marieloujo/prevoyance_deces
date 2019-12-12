<?php

namespace App\Http\Resources\Collection;
use App\Http\Resources\UserResources ;
use Illuminate\Http\Resources\Json\JsonResource;

class MarchandResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'matricule' => $this->matricule,
            'commission' => $this->commission,
            'credit_virtuel' => $this->credit_virtuel,
            'user' => new UserResources($this->user),
            
        ];
    }
}
