<?php

namespace App\Http\Resources\SuperMarchand;
use App\Http\Resources\ContratResources;
use App\Http\Resources\Marchand\MarchandResource;
use App\Http\Resources\MarchandResources;
use App\Http\Resources\SuperMarchandResources;

use App\Http\Resources\UsersResource;

use Illuminate\Http\Resources\Json\JsonResource;

class SuperMarchandResource extends JsonResource
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
            'super_marchand'    =>  new UsersResource($this->user),
            'marchands'         =>  MarchandResources::collection($this->marchands),
        ];
    }
}

