<?php

namespace App\Http\Resources\Collection;

use App\Http\Resources\UserResources;
use Illuminate\Http\Resources\Json\JsonResource;

class AssurerResource extends JsonResource
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
                'profession' => $this->profession,
                'employeur' => $this->employeur,
                'user' => new UserResources($this->user),
            ];
        
    }
}
