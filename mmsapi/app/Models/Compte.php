<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Compte extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $morphClass = 'App\Models\Compte'; 
    protected $fillable = [
        'montant','compte', 'compteable_id', 'compteable_type',
    ];

    public function compteable(){
        return $this->morphTo();
    }
}
